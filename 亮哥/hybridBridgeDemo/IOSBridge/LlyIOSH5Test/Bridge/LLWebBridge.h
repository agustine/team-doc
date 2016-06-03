//
//  LLWebBridge.h
//  LlyIOSH5Test
//
//  Created by 邓志亮 on 16/5/13.
//  Copyright © 2016年 邓志亮. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface LLWebBridge : NSObject

@property (nonatomic, strong) UIWebView *webView;

//构造函数
-(id)initWithWebView:(UIWebView*)webView;
//判断是否交互请求
- (BOOL)isHybridRequest:(NSURLRequest *)request;
//获取交互请求命令和参数
- (NSDictionary *)getRequestMessage;
//构造交互回调js字符串
- (NSString *)callBackStringFrom:(id)obj idStr:(NSString *)idStr isSuccess:(BOOL)isSuccess;
//执行js代码
- (NSString *)doJavaScriptString:(NSString *)script;

@end
