// miniprogram/pages/regist/regist.js
var app=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    genders: ['男', '女'],
    user: {
      "mobile": "",
      "pwd": "",
      "nickName": "",
      "gender": "男",
      "birthday": "",
      "city": "",
      "isValidate": "-1"
  },
    region: ['广东省', '广州市', '海珠区'],
    selectedCity: '',
    isSelectCity: false,
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  bindDateChange: function(e){
    this.setData({ 'user.birthday': e.detail.value})
  },

  bindNickNameChange: function(e){
    this.setData({ 'user.nickName': e.detail.value})
  },

  bindMobileChange: function (e) {
    this.setData({ 'user.mobile': e.detail.value })
  },

  bindGenderChange: function (e) {
    var idx = e.detail.value
    var gender = this.data.genders[idx]
    this.setData({ 'user.gender': gender })
  },

  bindYearChange: function (e) {
    this.setData({ 'user.playYear': e.detail.value })
  },

  bindPwdChange: function (e) {
    this.setData({ 'user.pwd': e.detail.value })
  },

  bindRegionChange: function(e){
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      region: e.detail.value,
      selectedCity: e.detail.value.join("-"),
      "user.city": e.detail.value.join("-"),
    })
  },

/**用户注册 */
  regedit: function() {
    wx.showLoading({
      title: '加载中',
    })
    var that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/user/reg',
      data: that.data.user,
      method: 'POST',
      success(res){
        console.log(res)
        wx.hideLoading()
        if(res.data.code == '0'){
          wx.showModal({
            showCancel: false,
            title: '提示',
            content: '注册成功！',
            success (res) {
              if (res.confirm) {
                wx.navigateTo({
                  url: '/pages/user/login/login?mobile='+that.data.user.mobile,
                })
              }
            }
          })
        }else{
          wx.showToast({
            title: '系统繁忙！',
            icon: 'error',
            duration: 1500
          })
        }
      }
    })
  }
})