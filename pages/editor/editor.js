// pages/editor/editor.js
var app = getApp();

Page({
	data: {
		contentId: null,
		contentTitle: '',
		contentTxt: '',
		contentImg: '',
		contentUrl: '',
		editorReady: false,
		placeholder: '开始创作您的内容...',
	},

	onLoad(options) {
		console.log('编辑器页面加载, globalData:', app.globalData);
		this.setData({
			baseUrl: app.globalData.baseUrl
		});

		// 如果有contentId，则是编辑模式，需要加载现有内容
		if (options.id) {
			this.setData({
				contentId: options.id
			});
			this.loadContent(options.id);
		}
	},

	onReady() {
		// 初始化富文本编辑器
		const that = this;
		wx.createSelectorQuery().select('#editor').context(function (res) {
			that.editorCtx = res.context;
			that.setData({
				editorReady: true
			});
		}).exec();
	},

	// 加载已有内容
	loadContent(id) {
		let self = this;
		wx.request({
			url: app.globalData.baseUrl + '/appinfo/content/getById',
			data: {
				id: id
			},
			success(res) {
				if (res.data.code == 200) {
					const content = res.data.data;
					self.setData({
						contentTitle: content.contentTitle,
						contentTxt: content.contentTxt,
						contentImg: content.contentImg,
						contentUrl: content.contentUrl
					});
					// 设置编辑器内容
					if (self.editorCtx) {
						self.editorCtx.setContents({
							html: content.contentTxt
						});
					}
				} else {
					wx.showToast({
						title: res.data.msg || '加载失败',
						icon: 'error'
					});
				}
			},
			fail(err) {
				console.log('加载内容失败:', err);
				wx.showToast({
					title: '加载失败',
					icon: 'error'
				});
			}
		});
	},

	// 标题输入
	onTitleInput(e) {
		this.setData({
			contentTitle: e.detail.value
		});
	},

	// URL输入
	onUrlInput(e) {
		this.setData({
			contentUrl: e.detail.value
		});
	},

	// 编辑器内容变化
	onEditorInput(e) {
		// 保存编辑器内容
		this.setData({
			contentTxt: e.detail.html
		});
	},

	// 选择图片
	chooseImage() {
		const that = this;
		wx.chooseImage({
			count: 1,
			sizeType: ['compressed'],
			sourceType: ['album', 'camera'],
			success(res) {
				const tempFilePath = res.tempFilePaths[0];
				that.uploadImage(tempFilePath);
			}
		});
	},

	// 上传图片
	uploadImage(filePath) {
		const that = this;
		wx.showLoading({
			title: '上传中...'
		});

		wx.uploadFile({
			url: app.globalData.baseUrl + '/appinfo/upload/image',
			filePath: filePath,
			name: 'file',
			success(res) {
				wx.hideLoading();
				const data = JSON.parse(res.data);
				if (data.code == 200) {
					that.setData({
						contentImg: data.data
					});
					wx.showToast({
						title: '上传成功',
						icon: 'success'
					});
				} else {
					wx.showToast({
						title: data.msg || '上传失败',
						icon: 'error'
					});
				}
			},
			fail(err) {
				wx.hideLoading();
				console.log('上传失败:', err);
				wx.showToast({
					title: '上传失败',
					icon: 'error'
				});
			}
		});
	},

	// 保存内容
	saveContent() {
		const that = this;
		const {
			contentId,
			contentTitle,
			contentTxt,
			contentImg,
			contentUrl
		} = this.data;

		// 验证必填项
		if (!contentTitle) {
			wx.showToast({
				title: '请输入标题',
				icon: 'none'
			});
			return;
		}

		if (!contentTxt) {
			wx.showToast({
				title: '请输入内容',
				icon: 'none'
			});
			return;
		}

		wx.showLoading({
			title: '保存中...'
		});

		const url = contentId ?
			app.globalData.baseUrl + '/appinfo/content/update' :
			app.globalData.baseUrl + '/appinfo/content/add';

		wx.request({
			url: url,
			method: 'POST',
			data: {
				id: contentId,
				contentTitle: contentTitle,
				contentTxt: contentTxt,
				contentImg: contentImg,
				contentUrl: contentUrl
			},
			header: {
				'content-type': 'application/json'
			},
			success(res) {
				wx.hideLoading();
				if (res.data.code == 200) {
					wx.showToast({
						title: '保存成功',
						icon: 'success',
						duration: 2000
					});
					setTimeout(() => {
						wx.navigateBack();
					}, 2000);
				} else {
					wx.showToast({
						title: res.data.msg || '保存失败',
						icon: 'error'
					});
				}
			},
			fail(err) {
				wx.hideLoading();
				console.log('保存失败:', err);
				wx.showToast({
					title: '保存失败',
					icon: 'error'
				});
			}
		});
	},

	// 预览
	previewContent() {
		const {
			contentTitle,
			contentTxt
		} = this.data;

		if (!contentTitle || !contentTxt) {
			wx.showToast({
				title: '请先填写标题和内容',
				icon: 'none'
			});
			return;
		}

		// 显示预览对话框
		wx.showModal({
			title: contentTitle,
			content: contentTxt.replace(/<[^>]+>/g, ''), // 移除HTML标签用于预览
			showCancel: false
		});
	}
});
