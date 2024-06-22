var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    newMsg: 0
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
    if(app.globalData.userInfo == null){
      wx.showModal({
        title: '提示',
        content: '请先登录',
        success (res) {
          if (res.confirm) {
            wx.navigateTo({
              url: '/pages/user/login/login',
            })
          } else if (res.cancel) {
            wx.switchTab({
              url: '/pages/pet/list',
            })
          }
        }
      })
    }else{
      let appInfo = app.globalData.userInfo
      let that = this
      console.log(appInfo)
      this.setData({
        userInfo : appInfo
      })
      wx.request({
        url: app.globalData.apiBaseUrl+'/user/loadNewMsg', 
        data: {},
        header: {
          'content-type': 'application/json' ,
          'Cookie': wx.getStorageSync('cookieKey')
        },
        success (res) {
          wx.hideLoading()
          that.setData({
            newMsg: res.data.data
          })
        }
      })
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  toMyAdopt: function() {
    wx.navigateTo({
      url: '/pages/user/adopt/adopt-list?type=1',
    })
  },

  toMyPetPublish: function() {
    wx.navigateTo({
      url: '/pages/user/adopt/adopt-list?type=2',
    })
  },

  toMyBlog: function() {
    wx.navigateTo({
      url: '/pages/user/blog/my-blog',
    })
  },

  toMySearch: function(){
    wx.navigateTo({
      url: '/pages/user/search/search-list',
    })
  },

  toMyPet: function(){
    wx.navigateTo({
      url: '/pages/user/pet/mypet',
    })
  },

  toValidate: function(){
    if(this.data.userInfo.isValidate == 1){
      wx.showToast({
        title: '已实名认证',
        icon: 'error'
      })
    }else{
      wx.navigateTo({
        url: '/pages/user/validate/validate',
      })
    }
  },

  toMsgCenter: function(){
    wx.navigateTo({
      url: '/pages/user/msg/message',
    })
  },
  
  logout: function(){
    wx.request({
      url: app.globalData.apiBaseUrl+'/user/logout',
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      data: {},
      method: 'GET',
      success(res){
        app.globalData.userInfo = null
        wx.switchTab({
          url: '/pages/pet/list',
        })
      }
    })
  }
})