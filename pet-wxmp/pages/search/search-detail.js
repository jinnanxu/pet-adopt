// pages/search/search-detail.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    petNotice: {},
    noticeid: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let noticeid = options.noticeid
    console.log(noticeid)
    this.setData({
      noticeid: noticeid
    })
    this.loadNoticeDetail(noticeid)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function (options) {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  toMsgForm: function(){
    let nid = this.data.petNotice.noticeId
    wx.navigateTo({
      url: '/pages/search/publish/clue?nid='+nid,
    })
  },

  loadNoticeDetail: function(noticeid){
    wx.showLoading({
      title: '正在加载',
    })
    let that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/search/loadDetail', 
      data: {noticeid: noticeid},
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      success (res) {
        wx.hideLoading()
        console.log(res.data)
        that.setData({
          petNotice: res.data.data
        })
      }
    })
  }
})