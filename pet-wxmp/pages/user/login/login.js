// miniprogram/pages/login/login.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    mobile: '18900005555',
    pwd: ''
  },

  onLoad: function(options){
    console.log('全局变量：', app.globalData)
    // this.setData({
    //   mobile: options.mobile
    // })
  },

  toReg: function(){
    wx.navigateTo({
      url: '/pages/user/regist/regist'
    })
  }, 
  toValidate: function(){
    wx.navigateTo({
      url: '/pages/user/validate/validate'
    })
  }, 

  login: function(){
    wx.showLoading({
      title: '正在登录...',
    })
    let that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/user/login',
      data: {'pwd': that.data.pwd, 'mobile': that.data.mobile},
      method: 'POST',
      success(res){
        console.log(res)
        console.log(res.header["Set-Cookie"])
        if(res.data.code == '0'){
          wx.showToast({
            title: '登录成功',
          })
          // wx.setStorage({
          //   data: res.data,
          //   key: 'curr_user',
          // })
          app.globalData.userInfo = res.data.data
          console.log('设置全局变量', app.globalData.userInfo)
          console.log('获取全局变量', app.globalData.userInfo)
          wx.setStorageSync("cookieKey", res.header["Set-Cookie"]);
          wx.switchTab({
            url: '/pages/pet/list',
          })
        }else{
          wx.showToast({
            title: '密码错误',
            icon: 'error'
          })
        }
      }
    })
  },

  inputMobile: function(e){
    this.setData({ mobile: e.detail.value })
  },

  inputPwd: function (e) {
    this.setData({ pwd: e.detail.value })
  },
  
  toValidate: function(e){
    wx.navigateTo({
      url: '/pages/user/profile'
    })
  }
})