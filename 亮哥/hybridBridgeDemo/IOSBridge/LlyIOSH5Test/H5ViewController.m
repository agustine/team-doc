//
//  ViewController.m
//  LlyIOSH5Test
//
//  Created by 邓志亮 on 16/5/10.
//  Copyright © 2016年 邓志亮. All rights reserved.
//

#import "H5ViewController.h"
#import "LayoutConst.h"
#import "LLWebBridge.h"
#import "LLJSONTool.h"
#import "BridgeConst.h"
#import "BasePlugin.h"

@interface H5ViewController ()<UIWebViewDelegate, UIScrollViewDelegate>{
    //main webview
    UIWebView *webView;
    LLWebBridge *webBridge;
    NSString* requestID;
    BOOL isAsync;
    
}

@end

@implementation H5ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    //初始化webview
    [self drawUI];
    
    //初始化 hybrid brige
    webBridge = [[LLWebBridge alloc]initWithWebView: webView];
    
    //加载页面
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:@"http://10.100.9.11:1111/"]];
//    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:@"http://192.168.1.104:1111/"]];
    [webView loadRequest:request];
    
    //注册通知，用于异步回调
    [self addNotification];
}


- (void)drawUI{
    webView = [[UIWebView alloc] initWithFrame:CGRectMake(0, 0, ScreenWidth, ScreenHeight - NavgationBarHeight)];
    
    webView.scalesPageToFit = YES;
    webView.scrollView.delegate = self;
    webView.delegate = self;
    
    [self.view addSubview:webView];
}


#pragma mark - UIWebViewDelegate begin ==>
//拦截页面资源请求
- (BOOL)webView:(UIWebView *)_webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType{
      
    if ([webBridge isHybridRequest:request]) {
        NSDictionary * msgDic = [webBridge getRequestMessage];
        
        requestID = [msgDic objectForKey:@"requestId"];
        isAsync = [[msgDic objectForKey:@"async"] boolValue];
        
        NSString *service = [msgDic objectForKey:@"service"];
        NSString *action = [msgDic objectForKey:@"action"];
        NSDictionary *params = [msgDic objectForKey:@"args"];
        
        [self doAction:service action:action params:params];
    }
    
    return YES;
}

#pragma mark - UIWebViewDelegate end <==

//执行交互
-(void)doAction: (NSString*)service action:(NSString*)action params:(NSDictionary*)params{
    Class name = NSClassFromString(service);
    BasePlugin* instance = [[name alloc]initWithParams:self params: params];
    SEL sel =  NSSelectorFromString(action);

    if([instance respondsToSelector:sel]){
        if (isAsync) {
            SuppressPerformSelectorLeakWarning(   //the code just only for ignore waring...
            [instance performSelector:sel];
            );
        }else{
            SuppressPerformSelectorLeakWarning(
            id result = [instance performSelector:sel];
            NSString* callbackJS = [webBridge callBackStringFrom:result idStr:requestID isSuccess:YES];
            [webBridge doJavaScriptString:callbackJS];
            );
            
        }
    }else{
        NSLog(@"action[%@]未找到", action);
    }
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - 通知，用于JS回调

- (void)addNotification{
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(notification:) name:@"CallBackNotification" object:nil];
}

- (void)notification:(NSNotification *)noti{
    [self callBackNotificationUseInfo:noti.userInfo reqID:requestID];
}

- (void)callBackNotificationUseInfo:(NSDictionary *)useInfo reqID:(NSString *)reqID {
    NSDictionary *dictionary = [NSDictionary dictionaryWithObjectsAndKeys:
                                [self getStatusByNotificationMsg:useInfo[ResultsStatusKey]],@"status",
                                useInfo[ResultsMessageKey],@"message",nil];
    NSString *jsonStr = [dictionary getJSONStringFromObject];
    NSString *script = [NSString stringWithFormat:@"llWebBridge.callBackJs('%@','%@', %d);", jsonStr, reqID, isAsync];
    [webBridge doJavaScriptString:script];
}

- (NSString *)getStatusByNotificationMsg:(NSString *)message
{
    if ([message isEqualToString:Success_Value]) {
        return SuccessID;
    }else if([message isEqualToString:Failure_Value]){
        return FailID;
    }else{
        return CanCelID;
    }
}

- (void)dealloc{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

@end
