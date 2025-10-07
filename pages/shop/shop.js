// pages/shop/shop.js

var app = getApp();

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		shoplist: [{
				id: 1,
				title: '单手剑',
				selected: true
			},
			{
				id: 2,
				title: '双手剑',
				selected: false
			},
			{
				id: 3,
				title: '长枪',
				selected: false
			},
			{
				id: 4,
				title: '盾牌',
				selected: false
			},
			{
				id: 5,
				title: '弓箭',
				selected: false
			},
			{
				id: 6,
				title: '回旋镖',
				selected: false
			},
			{
				id: 7,
				title: '法杖',
				selected: false
			},
			{
				id: 8,
				title: '钝器',
				selected: false
			},
			{
				id: 9,
				title: '匕首',
				selected: false
			}
		],
		goodslist: [],
		currentShop: 'goods-1',
		isLoggedIn: null,
	},


	selectShop(event) {
		let id = event.currentTarget.dataset.id;
		let list = this.data.shoplist;
		for (let i = 0; i < list.length; i++) {
			let selected = list[i].id == id;
			list[i].selected = selected;
		}
		this.setData({
			shoplist: list,
			currentShop: 'goods-' + id
		})
	},


	saveCar(event) {
		const isLoggedIn=this.data.isLoggedIn
		if (!isLoggedIn) {
			wx.showToast({
				title: "请登录",
				icon: 'error',
				duration: 2000
			})
		} else {
			let goodsId = event.currentTarget.dataset.id;
			let userid = app.globalData.user.id;
			let url = this.data.baseUrl + "/appinfo/car/save"
			wx.request({
				url: url,
				data: {
					'userId': userid,
					'goodsId': goodsId,
					'goodsNum': 1,
				},
				method: 'POST',
				success(res) {
					if (res.data.code == 200) {
						wx.showToast({
							title: '已加入购物车',
						})
					} else {
						wx.showToast({
							title: res.data.msg,
							icon: 'error',
							duration: 2000
						})
					}
				},
				fail(err) {
					console.log('car失败' + err)
				}
			})
		}

	},

	getGoodsList() {
		let self = this;
		wx.request({
			url: app.globalData.baseUrl + '/appinfo/goods/List',
			success(res) {
				if (res.data.code == 200) {
					self.setData({
						goodslist: res.data.data
					})
				} else {
					wx.showToast({
						title: '失败！',
						icon: 'error',
						duration: 2000
					})
				}
			},
			fail(err) {
				console.log('goods请求失败' + err)
			}
		})
	},



	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad(options) {
		console.log('页面加载, globalData:', app.globalData);
		console.log('baseUrl:', app.globalData.baseUrl);
		this.setData({
			baseUrl: app.globalData.baseUrl,
			isLoggedIn:app.globalData.isLoggedIn,
		})
		this.getGoodsList();
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