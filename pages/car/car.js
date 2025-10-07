// pages/car/car.js

var app = getApp();

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		toolText: '编辑',
		selectAll: false,
		totalPrice: 0, // 总价格
		selectedCount: 0, // 选中商品数量
		carlist: [],
		isLoggedIn:null,
	},

	getCarList() {
		let self = this;
		this.setData({
			selectAll: false
		});
		wx.request({
			url: app.globalData.baseUrl + '/appinfo/car/ListByUser?userId=' + app.globalData.user.id,
			success(res) {
				if (res.data.code == 200) {
					// 初始化时给每个商品添加selected字段
					let carlist = (res.data.data || []).map(item => {
						return {
							...item,
							selected: false // 默认未选中
						};
					});
					self.setData({
						carlist: carlist
					});
					// 初始化后计算总价
					self.calculateTotal();
				} else {
					wx.showToast({
						title: res.data.msg,
						icon: 'error',
						duration: 2000
					})
				}
			},
			fail(err) {
				console.log('carVO请求失败' + err)
			}
		})
	},

	numSub(event) {
		// 防抖处理：500ms内只能操作一次
		let now = Date.now();
		if (now - this.data.lastOperationTime < 500) {
			wx.showToast({
				title: '操作太快了',
				icon: 'none'
			});
			return;
		}
		this.setData({
			lastOperationTime: now
		});

		let url = this.data.baseUrl + "/appinfo/car/numSub"
		let userid = app.globalData.user.id;
		let that = this;
		let temp = this.data.carlist;
		let index = event.currentTarget.dataset.index;
		let carId = temp[index].id;
		temp[index].goodsNum = temp[index].goodsNum - 1;

		if (temp[index].goodsNum == 0) {
			temp = that.data.carlist.filter(item => item.id != carId);
		}
		// 更新总价
		this.calculateTotal();

		wx.request({
			url: url,
			data: {
				'userId': userid,
				'id': carId,
				'goodsNum': 1,
			},
			method: 'POST',
			success(res) {
				if (res.data.code == 200) {
					wx.showToast({
						title: res.data.msg,
					})
					that.setData({
						carlist: temp
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

	},

	numPlus(event) {
		// 防抖处理：500ms内只能操作一次
		let now = Date.now();
		if (now - this.data.lastOperationTime < 500) {
			wx.showToast({
				title: '操作太快了',
				icon: 'none'
			});
			return;
		}
		this.setData({
			lastOperationTime: now
		});

		let url = this.data.baseUrl + "/appinfo/car/numPlus"
		let userid = app.globalData.user.id;
		let that = this;
		let temp = this.data.carlist;
		let index = event.currentTarget.dataset.index;
		let carId = temp[index].id;
		temp[index].goodsNum = temp[index].goodsNum + 1;

		// 更新总价
		this.calculateTotal();
		wx.request({
			url: url,
			data: {
				'userId': userid,
				'id': carId,
				'goodsNum': 1,
			},
			method: 'POST',
			success(res) {
				if (res.data.code == 200) {
					wx.showToast({
						title: '购物车已更改',
					})
					that.setData({
						carlist: temp
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
				console.log('购物车商品数量添加失败' + err)
			}
		})
	},

	// 切换单个商品选中状态
	toggleSelect(event) {
		let userid = app.globalData.user.id;
		let index = event.currentTarget.dataset.index;
		let carlist = this.data.carlist;
		// 切换选中状态
		carlist[index].selected = !carlist[index].selected;
		// 更新全选状态和总价
		this.updateSelectAllStatus();
		this.calculateTotal();
		this.setData({
			carlist: carlist
		});
	},

	// 切换全选状态
	toggleSelectAll() {
		console.log('toggleSelectAll被调用');
		console.log('当前全选状态:', this.data.selectAll);
		console.log('当前购物车数据:', this.data.carlist);
		let selectAll = !this.data.selectAll;
		let carlist = this.data.carlist.map(item => {
			return {
				...item,
				selected: selectAll
			};
		});
		console.log('新的全选状态:', selectAll);
		console.log('更新后的购物车:', carlist);
		this.setData({
			selectAll: selectAll,
			carlist: carlist
		});

		// 计算总价
		this.calculateTotal();
	},

	// 更新全选按钮状态
	updateSelectAllStatus() {
		let carlist = this.data.carlist;
		let selectAll = carlist.length > 0 && carlist.every(item => item.selected);
		this.setData({
			selectAll: selectAll
		});
	},

	// 计算总价和选中数量
	calculateTotal() {
		let carlist = this.data.carlist;
		let totalPrice = 0;
		let selectedCount = 0;

		carlist.forEach(item => {
			if (item.selected) {
				// 计算单个商品总价：价格 × 数量
				let price = parseFloat(item.goodsPrice) || 0;
				let quantity = parseInt(item.goodsNum) || 0;
				totalPrice += price * quantity;
				selectedCount += quantity;
			}
		});

		this.setData({
			totalPrice: totalPrice,
			selectedCount: selectedCount
		});
	},

	// 立即支付
	payNow() {
		const selectedGoods = this.data.carlist.filter(item => item.selected);
		if (selectedGoods.length === 0) {
			wx.showToast({
				title: '请至少选择一件商品',
				icon: 'error',
				duration: 2000
			});
			return;
		}
		let userid = app.globalData.user.id;
		let url = this.data.baseUrl + "/appinfo/sysOrder/submitOrder";
		let totalPrice=this.data.totalPrice;
		wx.request({
			url: url,
			data: {
				'userId': userid,
				'totalPrice':totalPrice,
				'orderGoods': selectedGoods,
				'firstName':selectedGoods[0].goodsName,
    			'firstImg':selectedGoods[0].goodsImg,
			},
			method: 'POST',
			success: (res) => {
				if (res.data.code == 200) {
					wx.showToast({ title: '订单提交成功', icon: 'success' });
					this.clearSelectedItems(selectedGoods);
				} else {
					wx.showToast({ title: res.data.msg, icon: 'error' });
				}
			},
			fail: (err) => {
				console.log('订单提交失败', err);
				wx.showToast({ title: '网络错误', icon: 'error' });
			}
		});
	},

	// 清空已选中的商品
	clearSelectedItems(selectedGoods) {
		let url = this.data.baseUrl + "/appinfo/car/deleteOrderedGoods"
		let userid = app.globalData.user.id;
		 // 获取要删除的商品ID列表
		 let carIds = selectedGoods.map(item => item.id);
		 wx.request({
			 url: url,
			 data: {
				 'userId': userid,
				 'carIds': carIds
			 },
			 method: 'POST',
			 success: (res) => {
				 if (res.data.code == 200) {
					 // 从本地购物车移除已提交的商品
					 let newCarlist = this.data.carlist.filter(item => !item.selected);
					 this.setData({
						 carlist: newCarlist
					 });
					 this.calculateTotal();
					 console.log('删除已订购商品成功');
				 }
			 },
			 fail: (err) => {
				 console.log('删除已订购商品失败', err);
				 // 失败时重新加载购物车数据
				 this.getCarList();
			 }
		 });
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
		this.getCarList();
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
		console.log('购物车页面显示, globalData:', app.globalData);
		console.log('baseUrl:', app.globalData.baseUrl);
		this.getCarList();
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