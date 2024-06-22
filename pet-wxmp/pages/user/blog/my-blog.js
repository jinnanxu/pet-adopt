// pages/blog/list.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    blogList: []
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
    this.loadBlogList()
  },

  loadBlogList: function(){
    wx.showLoading({
      title: '正在加载',
    })
    let that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/blog/myblog', 
      data: {},
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      success (res) {
        wx.hideLoading()
        console.log(res.data)
        that.setData({
          blogList: res.data.data
        })
      }
    })
  },

  toBlogDetail: function(e){
    let blogid = e.currentTarget.dataset.blogid;
    console.log(blogid)
    wx.navigateTo({
      url: '/pages/blog/detail/blog-detail?type=2&blogid='+blogid,
    })
  }
})