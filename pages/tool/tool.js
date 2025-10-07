// pages/tool/tool.js

var app=getApp();
Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		toolId:null
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad(options) {
		console.log('金刚区详情页面加载, globalData:', app.globalData);
  		console.log('baseUrl:', app.globalData.baseUrl);
		const eventChannel = this.getOpenerEventChannel();
		this.setData({
            baseUrl: app.globalData.baseUrl
        });
		eventChannel.on('acceptToolId', (data) => {
			this.setData({
				toolId: data.toolId
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