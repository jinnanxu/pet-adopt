// pages/user/msg/message.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    msgList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.loadMsgList()
  },

  loadMsgList: function(){
    wx.showLoading({
      title: '正在加载',
    })
    let that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/user/myMsg', 
      data: {},
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      success (res) {
        wx.hideLoading()
        console.log(res.data)
        that.setData({
          msgList: res.data.data
        })
      }
    })
  },
  toMsgDetail: function(e){
    let mid = e.currentTarget.dataset.msgid
    wx.navigateTo({
      url: '/pages/user/msg/msg-detail?mid='+mid,
    })
  }
})