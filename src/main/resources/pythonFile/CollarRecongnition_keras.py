import tensorflow as tf
import numpy as np
from tensorflow.keras.layers import Conv2D, BatchNormalization, Activation, MaxPool2D, Dropout, Flatten, Dense
from tensorflow.keras import Model
import  cv2 as cv
import sys


np.set_printoptions(threshold=np.inf)

# fashion = tf.keras.datasets.fashion_mnist
# (x_train, y_train), (x_test, y_test) = fashion.load_data()
# x_train, x_test = x_train / 255.0, x_test / 255.0
# print("x_train.shape", x_train.shape)
# x_train = x_train.reshape(x_train.shape[0], 28, 28, 1)  # 给数据增加一个维度，使数据和网络结构匹配
# x_test = x_test.reshape(x_test.shape[0], 28, 28, 1)
# print("x_train.shape", x_train.shape)

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
checkpoint_save_path = "D:/meiya/daka/src/main/resources/pythonFile/checkpoint/res18.ckpt"


model.load_weights(checkpoint_save_path)


def collar_round_recongniton(img_path_in,face_x,face_y):
    predict_list = list()
    img = cv.imread(img_path_in)
    x_width = img.shape[0]
    y_width = img.shape[1]
    face_x = int(face_x)
    face_y = int(face_y)

    face_x_start_shang = face_x + int(x_width/9.5)
    face_x_end_xia = face_x + int(x_width/3.5)
    face_y_start_zuo = face_y - int(y_width/18)
    face_y_end_you = face_y + int(y_width/11)

    img_cut = img[face_x_start_shang:face_x_end_xia,face_y_start_zuo:face_y_end_you]
    #cv.imshow("img_cut",img_cut)
    #cv.waitKey()
    img=cv.resize(img_cut,(32,32))
    img = cv.cvtColor(img, cv.COLOR_RGB2GRAY)
    img = np.reshape(img , (32, 32, 1))
    img=img.astype(float)
    predict_list.append(img)
    predict_list = np.array(predict_list)
    # print(model.predict_(predict_list))
    # print((np.argmax(model.predict(predict_list), axis=1)[0]))
    # print(((model.predict(predict_list))))
    if((model.predict(predict_list)[0][0])>0.92):
        print(0)
    else:
        print(1)


    # predict_list = list()
    # img = cv.imread(img_path_in)
    # # cv.imshow("img_cut", img)
    # # cv.waitKey()
    # img = cv.resize(img, (32, 32))
    # img = cv.cvtColor(img, cv.COLOR_RGB2GRAY)
    # cv.imshow("img_cut_single", img)
    # cv.waitKey()
    # img = np.reshape(img, (32, 32, 1))
    # # cv.imwrite(img_path_in, img)
    # img = img.astype(float)
    # predict_list.append(img)
    # predict_list = np.array(predict_list)
    # # print(model.predict_(predict_list))
    # print(np.argmax(model.predict(predict_list), axis=1))
    # print(((model.predict(predict_list))))
    # if((model.predict(predict_list)[0][0])>0.85):
    #     print(0)
    # else:
    #     print(1)


if __name__ == '__main__':
    # collar_round_recongniton("D:\\yong4.jpg","20","601")
    collar_round_recongniton(sys.argv[1],sys.argv[2],sys.argv[3])







