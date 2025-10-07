// pages/details/details.js
var app=getApp();


Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		orderId:null,
		detailslist:[],
		baseUrl: '' 
	},


	getOrderList(){
		let self=this;
		// 安全检查
		if (!self.data.orderId) {
			console.log('orderId 为空');
			return;
		}
		wx.request({
			url: app.globalData.baseUrl+'/appinfo/sysOrderDetails/ListByOrder?orderId='+self.data.orderId, 
			success (res) {
				if(res.data.code==200){
					self.setData({
						detailslist:res.data.data
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
				console.log('orderVO请求失败'+err)
			}
		})
	},
	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad(options) {
		console.log('订单详情页面加载, globalData:', app.globalData);
  		console.log('baseUrl:', app.globalData.baseUrl);
		const eventChannel = this.getOpenerEventChannel();
		this.setData({
            baseUrl: app.globalData.baseUrl
        });
		eventChannel.on('acceptOrderId', (data) => {
			this.setData({
				orderId: data.orderId
			}, () => {
				this.getOrderList();
			});
		});
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