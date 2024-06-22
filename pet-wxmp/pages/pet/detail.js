// pages/pet/detail.js
var app = getApp()
var QQMapWX = require('../../utils/qqmap-wx-jssdk.min.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    petid: '',
    type: 'index',
    pet: [],
    distance: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let petid = options.petid
    let type = options.type
    this.setData({
      petid: petid,
      type: type
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
    this.loadPetDetail(this.data.petid)
  },

  loadPetDetail: function(petid){
    wx.showLoading({
      title: '正在加载',
    })
    let that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/pet/loadDetail', 
      data: {petid: petid},
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      success (res) {
        wx.hideLoading()
        console.log(res.data)
        that.setData({
          pet: res.data.data
        })
        that.calDistance(res.data.data.posLat, res.data.data.posLng)
      }
    })
  },
  back: function(){
    wx.navigateBack()
  },
  calDistance(lat, lng){
    var qqmapsdk = new QQMapWX({key: 'NXDBZ-PWQRD-3RK4F-HHB34-C2DPH-KBFBM'})
    var _this = this;
    console.log('经纬度！',lat, lng);
    qqmapsdk.calculateDistance({
      from: '', //若起点有数据则采用起点坐标，若为空默认当前地址
      to: lat+","+lng, //终点坐标
      success: function (res) { //成功后的回调
        console.log('计算距离！',res);
        var res = res.result;
        let hw = res.elements[0].distance //拿到距离(米)
        var dis = [];
        for (var i = 0; i < res.elements.length; i++) {
          dis.push(hw); //将返回数据存入dis数组，
        }
        if(dis < 1000){
          dis = dis+"m"
        }else{
          dis =(dis/1000).toFixed(2);
          dis = dis+"km"
        }
        _this.setData({ //设置并更新distance数据
          distance: dis
        });
      },
    });
  },

  accusation: function(e){
    let petid = e.currentTarget.dataset.petid
    wx.navigateTo({
      url: '/pages/pet/publish/publish-acc?petid='+petid,
    })
  },

  adopt: function(){
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
      let that = this
      wx.showModal({
        title: '提示',
        content: '确定申请领养该宠物？',
        success (res) {
          if (res.confirm) {
            //发送领养请求
            wx.request({
              url: app.globalData.apiBaseUrl+'/pet/adopt', 
              method: 'POST',
              data: {
                publishUser: that.data.pet.userId,
                petId: that.data.pet.petId
              },
              header: {
                'content-type': 'application/json' ,
                'Cookie': wx.getStorageSync('cookieKey')
              },
              success (res) {
                wx.hideLoading()
                wx.showModal({
                  showCancel: false,
                  title: '提示',
                  content: res.data.msg
                })
              }
            })
          } else if (res.cancel) {
            
          }
        }
      })
    }
  }
})