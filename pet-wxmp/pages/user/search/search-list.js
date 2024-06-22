// pages/search/list.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    noticeList: []
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
    this.loadNoticeList()
  },

  toNoticeDetail: function(e){
    let noticeid = e.currentTarget.dataset.noticeid;
    wx.navigateTo({
      url: '/pages/search/search-detail?noticeid='+noticeid,
    })
  },

  loadNoticeList: function(){
    wx.showLoading({
      title: '正在加载',
    })
    let that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/search/mysearch', 
      data: {},
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      success (res) {
        wx.hideLoading()
        console.log(res.data)
        that.setData({
          noticeList: res.data.data
        })
      }
    })
  },
})