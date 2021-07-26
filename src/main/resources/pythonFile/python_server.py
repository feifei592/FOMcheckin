import tensorflow as tf
import numpy as np
from tensorflow.keras.layers import Conv2D, BatchNormalization, Activation, MaxPool2D, Dropout, Flatten, Dense
from tensorflow.keras import Model
import  cv2 as cv
import flask
import json
from io import BytesIO
from PIL import Image


np.set_printoptions(threshold=np.inf)
# 实例化 flask
app = flask.Flask(__name__)

# 我们需要重新定义我们的度量函数，从而在加载模型时使用它
def auc(y_true, y_pred):
    auc = tf.metrics.auc(y_true, y_pred)[1]
    tf.keras.backend.get_session().run(tf.local_variables_initializer())
    return auc

class ResnetBlock(Model):

    def __init__(self, filters, strides=1, residual_path=False):
        super(ResnetBlock, self).__init__()
        self.filters = filters
        self.strides = strides
        self.residual_path = residual_path

        self.c1 = Conv2D(filters, (3, 3), strides=strides, padding='same', use_bias=False)
        self.b1 = BatchNormalization()
        self.a1 = Activation('relu')

        self.c2 = Conv2D(filters, (3, 3), strides=1, padding='same', use_bias=False)
        self.b2 = BatchNormalization()

        # residual_path为True时，对输入进行下采样，即用1x1的卷积核做卷积操作，保证x能和F(x)维度相同，顺利相加
        if residual_path:
            self.down_c1 = Conv2D(filters, (1, 1), strides=strides, padding='same', use_bias=False)
            self.down_b1 = BatchNormalization()

        self.a2 = Activation('relu')

    def call(self, inputs):
        residual = inputs  # residual等于输入值本身，即residual=x
        # 将输入通过卷积、BN层、激活层，计算F(x)
        x = self.c1(inputs)
        x = self.b1(x)
        x = self.a1(x)

        x = self.c2(x)
        y = self.b2(x)

        if self.residual_path:
            residual = self.down_c1(inputs)
            residual = self.down_b1(residual)

        out = self.a2(y + residual)  # 最后输出的是两部分的和，即F(x)+x或F(x)+Wx,再过激活函数
        return out


class ResNet18(Model):

    def __init__(self, block_list, initial_filters=64):  # block_list表示每个block有几个卷积层
        super(ResNet18, self).__init__()
        self.num_blocks = len(block_list)  # 共有几个block
        self.block_list = block_list
        self.out_filters = initial_filters
        self.c1 = Conv2D(self.out_filters, (3, 3), strides=1, padding='same', use_bias=False)
        self.d1 = Dropout(0.2)
        self.b1 = BatchNormalization()
        self.a1 = Activation('relu')
        self.blocks = tf.keras.models.Sequential()
        # 构建ResNet网络结构
        for block_id in range(len(block_list)):  # 第几个resnet block
            for layer_id in range(block_list[block_id]):  # 第几个卷积层

                if block_id != 0 and layer_id == 0:  # 对除第一个block以外的每个block的输入进行下采样
                    block = ResnetBlock(self.out_filters, strides=2, residual_path=True)
                else:
                    block = ResnetBlock(self.out_filters, residual_path=False)
                self.blocks.add(block)  # 将构建好的block加入resnet
            self.out_filters *= 2  # 下一个block的卷积核数是上一个block的2倍
        self.p1 = tf.keras.layers.GlobalAveragePooling2D()
        self.f1 = tf.keras.layers.Dense(2, activation='softmax', kernel_regularizer=tf.keras.regularizers.l2())

    def call(self, inputs):
        x = self.c1(inputs)
        x = self.d1(x)
        x = self.b1(x)
        x = self.a1(x)
        x = self.blocks(x)
        x = self.p1(x)
        y = self.f1(x)
        return y

model = ResNet18([2, 2, 2, 2])

model.compile(optimizer='adam',
              loss=tf.keras.losses.SparseCategoricalCrossentropy(from_logits=False),
              metrics=['sparse_categorical_accuracy'])

# checkpoint_save_path = "./checkpoint/res18.ckpt"
checkpoint_save_path = "D:/meiya/model/checkpoint/res18.ckpt"
#加载模型
model.load_weights(checkpoint_save_path)

@app.route("/predict", methods=["POST"])
def predict():
    global model
    img = flask.request.stream.read()
    f = BytesIO(img)
    image = Image.open(f)
    image = cv.cvtColor(np.asarray(image), cv.COLOR_RGB2BGR)
    image=cv.resize(image,(32,32))
    image = cv.cvtColor(image, cv.COLOR_RGB2GRAY)
    image = np.reshape(image , (32, 32, 1))
    image=image.astype(float)
    predict_list = list()
    predict_list.append(image)
    predict_list = np.array(predict_list)
    result = []
    if((model.predict(predict_list)[0][0])>0.9):
        result.append(0)
    else:
        result.append(1)
    rsp = flask.make_response(json.dumps(result))
    rsp.mimetype = 'application/json'
    rsp.headers['Connection'] = 'close'
    return rsp

if __name__ == '__main__':
    # collar_round_recongniton("D:\\yong5.jpg","16","688")
    # collar_round_recongniton(sys.argv[1],sys.argv[2],sys.argv[3])
    app.run(host='0.0.0.0',port='5000')







