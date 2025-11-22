// pages/try_to/try_to.js

var app = getApp();
Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		contentId: null,
		contentDetail: null
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad(options) {
		console.log('内容区详情页面加载, globalData:', app.globalData);
		console.log('baseUrl:', app.globalData.baseUrl);
		const eventChannel = this.getOpenerEventChannel();
		this.setData({
			baseUrl: app.globalData.baseUrl
		});
		eventChannel.on('acceptContentId', (data) => {
			this.setData({
				contentId: data.contentId
			});
			this.loadContentDetail(data.contentId);
		});
	},

	/**
	 * 加载内容详情
	 */
	loadContentDetail(id) {
		let self = this;
		wx.request({
			url: app.globalData.baseUrl + '/appinfo/content/getById',
			data: {
				id: id
			},
			success(res) {
				if (res.data.code == 200) {
					self.setData({
						contentDetail: res.data.data
					});
				}
			},
			fail(err) {
				console.log('加载内容失败:', err);
			}
		});
	},

	/**
	 * 编辑内容
	 */
	editContent() {
		const contentId = this.data.contentId;
		wx.navigateTo({
			url: `/pages/editor/editor?id=${contentId}`
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