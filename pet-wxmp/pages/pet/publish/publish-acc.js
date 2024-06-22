// pages/pet/publish/publish-a.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    petId: '',
    reason: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let petid = options.petid
    this.setData({
      petId: petid
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

  submitAcc: function(){
    wx.showLoading({
      title: '正在加载',
    })
    let reqData = {
      petId: this.data.petId,
      reason: this.data.reason
    } 
    console.log(reqData)
    wx.request({
      url: app.globalData.apiBaseUrl+'/pet/publishAcc', 
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
            content:'提交成功',
            success(res){
              wx.navigateBack()
            }
          })
        }else if(res.data.code == "301"){
          wx.showModal({
            title: '提示',
            content: '请登录后再提交，是否马上登录？',
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
    this.setData({ reason: e.detail.value })
  },
})