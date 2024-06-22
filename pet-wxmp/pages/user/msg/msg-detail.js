// pages/user/msg/msg-detail.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    msg: {},
    mid: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let mid = options.mid
    //this.loadMsgDetail(mid)
    this.setData({
      mid: mid
    })
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
    this.loadMsgDetail()
  },

  loadMsgDetail: function(){
    let mid = this.data.mid
    wx.showLoading({
      title: '正在加载',
    })
    let that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/user/msgDetail?mid='+mid, 
      data: {},
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      success (res) {
        wx.hideLoading()
        console.log(res.data)
        that.setData({
          msg: res.data.data
        })
      }
    })
  },
  back: function(){
    wx.navigateBack()
  }
})