// pages/publish/publish.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    petImg: '',
    genders: ['男', '女', '不详'],
    jys: ['已绝育', '未绝育', '不详'],
    mys: ['已免疫', '未免疫', '不详'],
    pet: {
      gender: '男',
      jieyu: '已绝育',
      mianyi: '已免疫',
      subCategory: '',
      category: '',
      title: '',
      detail: '',
      pic: '',
      posTxt: '',
      posLat: '',
      posLng: ''
    },
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

  },

  //选择宠物图片
  chooseImg: function(){
    let _this = this
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success (res) {
        console.log(res)
        // tempFilePath可以作为img标签的src属性显示图片
        const tempFilePaths = res.tempFilePaths
        var tmpFileName = tempFilePaths[0].substring(tempFilePaths[0].lastIndexOf('/')+1)
        wx.uploadFile({
          url: 'http://118.25.89.125:16602/upload-server/upload', //图片服务器
          filePath: tempFilePaths[0],
          name: 'myFile',
          formData: {
            'user': 'test',
            'fileName': tmpFileName,
            'u': '98344921-326f-471e-b735-792eacfecc53'
          },
          success (res){
            let resjson = JSON.parse(res.data)
            console.log(resjson.url)
            _this.setData({
              petImg: resjson.url,
              'pet.pic': resjson.url
            })
            //do something
          }
        })
      }
    })
  },

  bindLocationInput: function(e){
    this.setData({ 'pet.location': e.detail.value })
  },
  bindMyChange: function(e){
    var idx = e.detail.value
    var my = this.data.mys[idx]
    this.setData({ 'pet.mianyi': my })
  },
  bindJyChange: function(e){
    this.setData({ 'pet.jieyu': this.data.jys[e.detail.value] })
  },
  bindGenderChange: function(e){
    this.setData({ 'pet.gender': this.data.genders[e.detail.value] })
  },
  bindCatInput: function(e){
    this.setData({ 'pet.category': e.detail.value })
  },
  bindTitleInput: function(e){
    this.setData({ 'pet.title': e.detail.value })
  },
  bindDetailInput: function(e){
    this.setData({ 'pet.detail': e.detail.value })
  },
  
  bindSubCatInput: function(e){
    this.setData({ 'pet.subCategory': e.detail.value })
  },
  bindAgeInput: function(e){
    this.setData({ 'pet.age': e.detail.value })
  },

  /**
   * 提交发布宠物送养表单
   */
  publishPet: function(){
    wx.showLoading({
      title: '加载中',
    })
    var that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/pet/publishAdopt',
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      data: that.data.pet,
      method: 'POST',
      success(res){
        console.log(res)
        wx.hideLoading()
        if(res.data.code == "0"){
          wx.showModal({
            title: '提示',
            content: '发布成功！',
            showCancel: false,
            success (res2) {
              if (res2.confirm) {
                wx.switchTab({
                  url: '/pages/pet/list',
                })
              }
            }
          })
        }else if(res.data.code == "301"){
          wx.showModal({
            title: '提示',
            content: '请登录后再发布送养，是否马上登录？',
            success (res2) {
              if (res2.confirm) {
                wx.navigateTo({
                  url: '/pages/user/login/login',
                })
              }
            }
          })
        }else{
          wx.showToast({
            title: '系统繁忙，发布失败！',
            icon: 'error'
          })
        }
      }
    })
  
  },

  getUserLocation: function () {
    wx.showLoading({
      title: '正在定位...',
    })
    let vm = this;
    wx.getSetting({
      success: (res) => {
        console.log(JSON.stringify(res))
        // res.authSetting['scope.userLocation'] == undefined    表示 初始化进入该页面
        // res.authSetting['scope.userLocation'] == false    表示 非初始化进入该页面,且未授权
        // res.authSetting['scope.userLocation'] == true    表示 地理位置授权
        if (res.authSetting['scope.userLocation'] != undefined && res.authSetting['scope.userLocation'] != true) {
          wx.showModal({
            title: '请求授权当前位置',
            content: '需要获取您的地理位置，请确认授权',
            success: function (res) {
              if (res.cancel) {
                wx.showToast({
                  title: '拒绝授权',
                  icon: 'none',
                  duration: 1000
                })
              } else if (res.confirm) {
                wx.openSetting({
                  success: function (dataAu) {
                    if (dataAu.authSetting["scope.userLocation"] == true) {
                      wx.showToast({
                        title: '授权成功',
                        icon: 'success',
                        duration: 1000
                      })
                      //再次授权，调用wx.getLocation的API
                      vm.getLocation();
                    } else {
                      wx.showToast({
                        title: '授权失败',
                        icon: 'none',
                        duration: 1000
                      })
                    }
                  }
                })
              }
            }
          })
        } else if (res.authSetting['scope.userLocation'] == undefined) {
          //调用wx.getLocation的API
          vm.getLocation();
        }
        else {
          //调用wx.getLocation的API
          vm.getLocation();
        }
      }
    })
  },
  getLocation: function() {
    const that = this
    wx.getLocation({
      type: 'wgs84',
      altitude: true,
      isHighAccuracy: true,
      highAccuracyExpireTime: 2000,
      success: function(res) {
        wx.hideLoading()
        console.log(res)
        that.setData({
          latitude: res.latitude,
          longitude: res.longitude,
          'pet.posLat': res.latitude,
          'pet.posLng': res.longitude
        })
        // 构建请求地址
        // 逆解析接口 /ws/geocoder/v1
        var qqMapApi = 'http://apis.map.qq.com/ws/geocoder/v1/' + "?location=" + that.data.latitude + ',' +
          that.data.longitude + "&key=" + 'NXDBZ-PWQRD-3RK4F-HHB34-C2DPH-KBFBM' + "&get_poi=1";
        that.sendRequest(qqMapApi);
      },
      fail: function() {
        wx.showToast({
          title: '获取位置信息失败',
          icon: 'none'
        })
      }
    })
  },

  sendRequest:function(qqMapApi) {
    const that = this
    wx.request({
      url: qqMapApi,
      header: {
        'Content-Type': 'application/json'
      },
      data: {},
      method:'GET',
      success: (res) => {
        console.log(res)
        if (res.statusCode == 200 && res.data.status == 0) {
          // 从返回值中提取需要的业务地理信息数据 国家、省、市、县区、街道
          // that.setData({ 'address.province': res.data.result.address_component.province });
          // that.setData({ 'address.city': res.data.result.address_component.city });
          // that.setData({ 'address.district': res.data.result.address_component.district });
          // that.setData({ 'address.street': res.data.result.address_component.street });
          that.setData({ 'pet.posTxt': res.data.result.address });
          that.setData({ 'pet.posLat': res.data.result.location.lat });
          that.setData({ 'pet.posLng': res.data.result.location.lng });
        }
      }
    })
  },
})