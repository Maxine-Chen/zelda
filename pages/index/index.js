// index.js
var app = getApp();

Page({
	data: {
		array1:[],
		content: [],
		banner: [],
		indicatorDots: true,
		vertical: false,
		autoplay: true,
		circular: true,
		interval: 2000,
		duration: 500,
		isLoggedIn: null,
		username: null,
	},

	//页面加载时
	onLoad() {
		this.setData({
			baseUrl: app.globalData.baseUrl,
			username: app.globalData.user.name,
		})
		this.getBannerList();
		this.getToolList();
		this.getContentList();
		this.checkLoginStatus();
	},

	onShow() {
		this.checkLoginStatus();
	},

	getBannerList() {
		let self = this;
		wx.request({
			url: app.globalData.baseUrl + '/appinfo/banner/List',
			success(res) {
				if (res.data.code == 200) {
					self.setData({
						banner: res.data.data
					})
				} else {
					wx.showToast({
						title: 'res.data.msg',
						icon: 'error',
						duration: 2000
					})
				}
			},
			fail(err) {
				console.log('banner请求失败' + err)
			}
		})
	},

	getToolList() {
		let self = this;
		wx.request({
			url: app.globalData.baseUrl + '/appinfo/tool/List',
			success(res) {
				if (res.data.code == 200) {
					self.setData({
						array1: res.data.data
					})
				} else {
					wx.showToast({
						title: 'res.data.msg',
						icon: 'error',
						duration: 2000
					})
				}
			},
			fail(err) {
				console.log('tool请求失败' + err)
			}
		})
	},

	getContentList() {
		let self = this;
		wx.request({
			url: app.globalData.baseUrl + '/appinfo/content/List',
			success(res) {
				if (res.data.code == 200) {
					self.setData({
						content: res.data.data
					})
				} else {
					wx.showToast({
						title: 'res.data.msg',
						icon: 'error',
						duration: 2000
					})
				}
			},
			fail(err) {
				console.log('content请求失败' + err)
			}
		})
	},

	onGridItemClick: function (event) {
		// 1. 获取通过 data-url 传递过来的页面路径
		/*点击的图标（target）没有url属性，冒泡到currenttarget，获得url属性*/
		let toolId = event.currentTarget.dataset.id;
		const pagePath = event.currentTarget.dataset.url;
		// 2. 简单校验路径是否存在
		if (!pagePath) {
			wx.showToast({
				title: '功能开发中~',
				icon: 'none'
			});
			return;
		}
		// 3. 使用小程序路由API跳转到指定页面
		wx.navigateTo({ //非tabbar页面
			url: pagePath, // 跳转的目标路径
			success: (res) => {
				res.eventChannel.emit('acceptToolId', {
					toolId: toolId
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

	onContentItemClick(event) {
		let contentId = event.currentTarget.dataset.id;
		const pagePath = event.currentTarget.dataset.url;
		console.log("con点击");
		// 2. 简单校验路径是否存在
		if (!pagePath) {
			wx.showToast({
				title: '功能开发中~',
				icon: 'none'
			});
			return;
		}
		// 3. 使用小程序路由API跳转到指定页面
		wx.navigateTo({ //非tabbar页面
			url: pagePath, // 跳转的目标路径
			success: (res) => {
				res.eventChannel.emit('acceptContentId', {
					contentId:contentId
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

	// 检查登录状态
	checkLoginStatus() {
		this.setData({
			isLoggedIn: app.globalData.isLoggedIn,
			username: app.globalData.user.name,
		});
		console.log("isLoggedIn:", this.data.isLoggedIn);
	},

	// 处理登录
	handleLogin() {
		wx.navigateTo({
			url: '/pages/login/login'
		});
	},

	// 处理退出
	handleLogout() {
		wx.showModal({
			title: '提示',
			content: '确定要退出登录吗？',
			success: (res) => {
				app.globalData.isLoggedIn = false;
				this.setData({
					isLoggedIn: false
				});
				wx.showToast({
					title: '已退出登录',
					icon: 'success'
				});

			}
		});
	},
})