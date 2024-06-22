// pages/blog/detail/blog-detail.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    blogid: '',
    blog: {},
    btype: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log('KKK',options)
    let blogidpara = options.blogid
    let type = options.type
    console.log(blogidpara)
    this.setData({
      blogid: blogidpara,
      btype: type
    })
  },

  onHide:function(){
    
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function (options) {
    console.log('SSS',options)
    this.loadBlogDetail(this.data.blogid)
  },

  loadBlogDetail: function(blogid){
    wx.showLoading({
      title: '正在加载',
    })
    let that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/blog/loadDetail', 
      data: {blogid: blogid},
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      success (res) {
        wx.hideLoading()
        console.log(res.data)
        that.setData({
          blog: res.data.data
        })
      }
    })
  },

  back: function(){
    wx.navigateBack()
  },

  accusation: function(e){
    let petid = this.data.blog.pet.petId
    wx.navigateTo({
      url: '/pages/pet/publish/publish-acc?petid='+petid,
    })
  }
})