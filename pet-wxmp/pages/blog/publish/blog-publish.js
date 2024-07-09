// pages/publish/publish.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    blog: {
      title: '',
      pic: '',
      content: '',
      petId: ''
    },
    petList:[],
    subCategory: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.loadMyPets()
  },

  //选择宠物图片
  chooseImg: function(){
    let _this = this
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success (res) {
        // tempFilePath可以作为img标签的src属性显示图片
        const tempFilePaths = res.tempFilePaths
        var tmpFileName = tempFilePaths[0].substring(tempFilePaths[0].lastIndexOf('/')+1)
        wx.uploadFile({
          url: 'http://118.125.89.125:1602/upload-server/upload', //图片服务器
          filePath: tempFilePaths[0],
          name: 'myFile',
          formData: {
            'user': 'test',
            'fileName': tmpFileName,
            'u': '98344921-326f-471e-b735-792eacfecc53'
          },
          success (res){
            let resjson = JSON.parse(res.data)
            console.log(resjson.url)
            _this.setData({
              'blog.pic': resjson.url
            })
            //do something
          }
        })
      }
    })
  }, 

  loadMyPets: function(){
    wx.showLoading({
      title: '正在加载',
    })
    let that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/blog/loadMyAdopt', 
      data: {},
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      success (res) {
        wx.hideLoading()
        console.log(res.data)
        that.setData({
          petList: res.data.data
        })
      }
    })
  },
  bindPetChange: function(e){
    var idx = e.detail.value
    var selPet = this.data.petList[idx]
    this.setData({ 
      subCategory: selPet.subCategory,
      'blog.petId': selPet.petId
    })
  },

  bindTitleInput: function(e){
    this.setData({ 'blog.title': e.detail.value })
  },
  bindContentInput: function(e){
    this.setData({ 'blog.content': e.detail.value })
  },

  submitBlog: function(){
    //组装参数
    let reqData = this.data.blog
    wx.showLoading({
      title: '加载中',
    })
    var that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/blog/publishBlog',
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      data: reqData,
      method: 'POST',
      success(res){
        console.log(res)
        wx.hideLoading()
        if(res.data.code == "0"){
          wx.showModal({
            title: '提示',
            content: '发布成功！',
            showCancel: false,
            success (res2) {
              if (res2.confirm) {
                wx.switchTab({
                  url: '/pages/blog/list',
                })
              }
            }
          })
        }else if(res.data.code == "MATCH_ERROR"){
          wx.showModal({
            title: '提示',
            showCancel: false,
            content: '您上传的图片与领养的宠物不符！',
            success (res2) {}
          })
        }else if(res.data.code == "301"){
          wx.showModal({
            title: '提示',
            content: '请登录后再发布动态，是否马上登录？',
            success (res2) {
              if (res2.confirm) {
                wx.navigateTo({
                  url: '/pages/user/login/login',
                })
              }
            }
          })
        }else{
          wx.showToast({
            title: '系统繁忙，发布失败！',
            icon: 'error'
          })
        }
      }
    })
  }
})
