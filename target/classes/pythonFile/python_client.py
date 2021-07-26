import requests
import os
import json
import tensorflow as tf
import numpy as np
from tensorflow.keras.layers import Conv2D, BatchNormalization, Activation, MaxPool2D, Dropout, Flatten, Dense
from tensorflow.keras import Model
import  cv2 as cv
import sys
import flask
import json
from io import BytesIO
from PIL import Image



def collar_round_recongniton(img_path_in,face_x,face_y):
    img = cv.imread(img_path_in)
    x_width = img.shape[0]
    y_width = img.shape[1]
    face_x = int(face_x)
    face_y = int(face_y)

    face_x_start_shang = face_x + int(x_width/9.5)
    face_x_end_xia = face_x + int(x_width/5)
    face_y_start_zuo = face_y - int(y_width/21)
    face_y_end_you = face_y + int(y_width/9)

    img_cut = img[face_x_start_shang:face_x_end_xia,face_y_start_zuo:face_y_end_you]
    # cv.imshow("img_cut",img_cut)
    # cv.waitKey()
    img=cv.resize(img_cut,(32,32))
    img = cv.cvtColor(img, cv.COLOR_RGB2GRAY)
    img = np.reshape(img , (32, 32, 1))
    collar_cut_pic = "D:\\meiya\\image\\collar\\collar.jpg"
    cv.imwrite(collar_cut_pic, img)

    url = 'http://127.0.0.1:5000/predict'
    headers = {'Content-Type': 'image/jpeg'}

    files = {'media': open(collar_cut_pic, 'rb')}  # 你要预测的图片
    requests.post(url, files=files)

    data = open(collar_cut_pic, 'rb').read()
    r = requests.post(url, data=data, headers=headers, verify=False)
    print(r.text[1])


if __name__ == '__main__':
    # collar_round_recongniton("D:\\yong5.jpg","16","688")
    collar_round_recongniton(sys.argv[1],sys.argv[2],sys.argv[3])
