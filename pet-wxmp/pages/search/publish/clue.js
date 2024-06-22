// pages/search/publish/clue.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    content: '',
    noticeId: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      noticeId: options.nid
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

  },

  submitClue: function(){
    wx.showLoading({
      title: '正在加载',
    })
    let reqData = {
      title: '寻宠线索',
      content: this.data.content,
      noticeId: this.data.noticeId
    } 
    console.log(reqData)
    wx.request({
      url: app.globalData.apiBaseUrl+'/search/publishClue', 
      method: 'POST',
      data: reqData,
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      success (res) {
        wx.hideLoading()
        console.log(res.data)
        if(res.data.code == "0"){
          wx.showModal({
            showCancel: false,
            title: '提示',
            content:'线索发送成功，感谢您的善意',
            success(res){
              wx.navigateBack()
            }
          })
        }else if(res.data.code == "301"){
          wx.showModal({
            title: '提示',
            content: '请登录后再发布线索，是否马上登录？',
            success (res2) {
              if (res2.confirm) {
                wx.navigateTo({
                  url: '/pages/user/login/login',
                })
              }
            }
          })
        }
      }
    })
  },
  bindDetailInput: function(e){
    this.setData({ content: e.detail.value })
  },
})