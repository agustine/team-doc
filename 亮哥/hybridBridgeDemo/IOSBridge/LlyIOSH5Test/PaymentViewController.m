//
//  PaymentController.m
//  LlyIOSH5Test
//
//  Created by 邓志亮 on 16/5/13.
//  Copyright © 2016年 邓志亮. All rights reserved.
//

#import "PaymentViewController.h"
#import "LayoutConst.h"
#import "BridgeConst.h"

@implementation PaymentViewController

UIWebView *webView;

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self drawUI];
}


//绘制页面按钮
- (void)drawUI{
    self.view.backgroundColor = [UIColor whiteColor];
    [self drawButton:@"支付" pointY:100 event:@selector(payClick:)];
    [self drawButton:@"取消" pointY:200 event:@selector(cancelClick:)];

}

- (void)drawButton:(NSString*)title pointY:(int)pointY event:(SEL)event{
    int buttonWidth = 200, buttonHeight=50;
    UIButton *btn = [[UIButton alloc] initWithFrame:CGRectMake(ScreenWidth/2-buttonWidth/2, pointY, buttonWidth, buttonHeight)];
    [btn setTitle:title forState:UIControlStateNormal];
    btn.backgroundColor = [UIColor orangeColor];
    [btn addTarget:self action:event forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
}


- (IBAction)payClick:(id)sender {
    //params verify...
    
    if ([[[self payParams] objectForKey:@"channel"] isEqualToString:@""]) {     //fail
        [[NSNotificationCenter defaultCenter] postNotificationName:CallBackNotification object:nil userInfo:[NSDictionary  dictionaryWithObjectsAndKeys:Failure_Value,ResultsStatusKey,
            @"支付渠道不能为空", ResultsMessageKey, nil]];
        [self.navigationController popViewControllerAnimated:YES];
    }else{      //success
        //do sth.
        [[NSNotificationCenter defaultCenter] postNotificationName:CallBackNotification object:nil userInfo:[NSDictionary  dictionaryWithObjectsAndKeys:Success_Value,ResultsStatusKey,nil]];
        [self.navigationController popViewControllerAnimated:YES];
    }
}


- (IBAction)cancelClick:(id)sender {
    //cancel
    [[NSNotificationCenter defaultCenter] postNotificationName:CallBackNotification object:nil userInfo:[NSDictionary  dictionaryWithObjectsAndKeys:Cancel_Value,ResultsStatusKey,nil]];
    [self.navigationController popViewControllerAnimated:YES];

}

@end
