// pages/user/adopt/adopt-list.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    dataType: 1,
    adoptList0: [],
    adoptList1: [],
    winWidth: 0,
    winHeight: 0,
    currentTab: 0,
    currAdopt: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      dataType: options.type
    })
    var that = this;
    /**
     * 获取当前设备的宽高
     */
    wx.getSystemInfo( {
      success: function( res ) {
          that.setData( {
              winWidth: res.windowWidth,
              winHeight: res.windowHeight
          });
      }
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.loadAdoptList()
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  swichNav: function( e ) {
    var that = this;
    if( this.data.currentTab === e.target.dataset.current ) {
      return false;
    } else {
      that.setData( {
          currentTab: e.target.dataset.current
      })
    }
  },

  bindChange: function( e ) {
    var that = this;
    that.setData( { currentTab: e.detail.current });
  },

  loadAdoptList: function(type){

    //1为我的领养，2为我的送养
    wx.showLoading({
      title: '正在加载',
    })
    let that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/pet/myadopt', 
      data: {type: this.data.dataType},
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      success (res) {
        wx.hideLoading()
        console.log(res.data)
        that.setData({
          adoptList0: res.data.data[0],
          adoptList1: res.data.data[1],
        })
      }
    })
  },

  agreeApp: function(e){
    let aid = e.currentTarget.dataset.adoptid
    this.setData({currAdopt: aid})
    let that = this
    wx.showModal({
      title: '提示',
      content: '是否同意该用户的申请？同意后其他申请人将自动被拒绝。',
      success (res) {
        if (res.confirm) {
          wx.request({
            url: app.globalData.apiBaseUrl+'/pet/appAdopt', 
            data: {adoptId: aid},
            header: {
              'content-type': 'application/json' ,
              'Cookie': wx.getStorageSync('cookieKey')
            },
            success (res) {
              wx.hideLoading()
              console.log(res.data)
              wx.showModal({
                title: '提示',
                content: res.data.msg,
                showCancel: false,
                success (res) {
                  if (res.confirm) {
                    that.loadAdoptList()
                  }
                }
              })
            }
          })
        }
      }
    })
  }
})