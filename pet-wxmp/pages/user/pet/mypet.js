// pages/pet/list.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    petList: []
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
    this.loadPetList()
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  toPublishSearch: function(){
    if(app.globalData.userInfo == null){
      wx.showModal({
        title: '提示',
        content: '当前用户未登录，是否马上登录？',
        success (res2) {
          if (res2.confirm) {
            wx.navigateTo({
              url: '/pages/user/login/login',
            })
          }
        }
      })
    }else{
      wx.navigateTo({
        url: '/pages/search/publish/publish',
      })
    }
  },

  toPublishBlog: function(){
    if(app.globalData.userInfo == null){
      wx.showModal({
        title: '提示',
        content: '当前用户未登录，是否马上登录？',
        success (res2) {
          if (res2.confirm) {
            wx.navigateTo({
              url: '/pages/user/login/login',
            })
          }
        }
      })
    }else{
      wx.navigateTo({
        url: '/pages/blog/publish/blog-publish',
      })
    }
  },

  toSearchList: function(){
    wx.navigateTo({
      url: '/pages/search/list',
    })
  },

  loadPetList: function(){
    wx.showLoading({
      title: '正在加载',
    })
    let that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/pet/myPetList', 
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
  
  //跳到详情页面
  toPetDetail:function(e){
    let petid = e.currentTarget.dataset.petid
    wx.navigateTo({
      url: '/pages/pet/detail?type=my&petid='+petid,
    })
  }
})