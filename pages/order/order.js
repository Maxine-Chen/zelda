// pages/order/order.js

var app=getApp();

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		orderlist:[],
		isLoggedIn:null,
	},

	getOrderList(){
		let self=this;
		wx.request({
			url: app.globalData.baseUrl+'/appinfo/sysOrder/ListByUser?userId=' + app.globalData.user.id, 
			success (res) {
				if(res.data.code==200){
					self.setData({
						orderlist:res.data.data
					});
				}	
				else{
					wx.showToast({
						title:res.data.msg,
						icon: 'error',
						duration: 2000
					  })
				}			
			},
			fail(err){
				console.log('订单列表请求失败'+err)
			}
		})
	},

	toOrderDetails: function(event) {
		const id = event.currentTarget.dataset.id;
		const pagePath = "../details/details";
		console.log('订单ID:', id);
		// 跳转到订单详情页
		wx.navigateTo({	//非tabbar页面
			url: pagePath, // 跳转的目标路径
			success: (res) => {
				res.eventChannel.emit('acceptOrderId', { 
					orderId: id 
				});
			  },
			fail: (err) => {
			  // 跳转失败的处理（例如页面不存在）
			  console.error('跳转失败:', err);
			  wx.showToast({
				title: '页面不存在',
				icon: 'error'
			  });
			}
		});
	  },
	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad(options) {
		console.log('订单页面加载, globalData:', app.globalData);
  		console.log('baseUrl:', app.globalData.baseUrl);
		this.setData({
			baseUrl:app.globalData.baseUrl,
			isLoggedIn:app.globalData.isLoggedIn,
		})
		this.getOrderList();
	},

	/**
	 * 生命周期函数--监听页面初次渲染完成
	 */
	onReady() {

	},

	/**
	 * 生命周期函数--监听页面显示
	 */
	onShow() {
		console.log('订单页面显示, globalData:', app.globalData);
  	console.log('baseUrl:', app.globalData.baseUrl);
		this.getOrderList();
		this.setData({
			isLoggedIn:app.globalData.isLoggedIn,
		});
	},

	/**
	 * 生命周期函数--监听页面隐藏
	 */
	onHide() {

	},

	/**
	 * 生命周期函数--监听页面卸载
	 */
	onUnload() {

	},

	/**
	 * 页面相关事件处理函数--监听用户下拉动作
	 */
	onPullDownRefresh() {

	},

	/**
	 * 页面上拉触底事件的处理函数
	 */
	onReachBottom() {

	},

	/**
	 * 用户点击右上角分享
	 */
	onShareAppMessage() {

	}
})