//
//  LLWebBridge.m
//  LlyIOSH5Test
//
//  Created by 邓志亮 on 16/5/13.
//  Copyright © 2016年 邓志亮. All rights reserved.
//

#import "LLWebBridge.h"
#import "LLJSONTool.h"

@interface LLWebBridge ()

@end

@implementation LLWebBridge


-(id)initWithWebView:(UIWebView*)webView{
    self = [super init];
    if(self != nil){
        _webView = webView;
    }
    return self;
}

- (BOOL)isHybridRequest:(NSURLRequest *)request{
    if (([[[request URL] scheme] isEqualToString:@"llyhybrid"])
        && ([[[request URL] host] isEqualToString:@"ready"])) {
        
        return YES;
    }
    return NO;
}


- (NSDictionary *)getRequestMessage{
    NSString *msg = [self doJavaScriptString:[NSString stringWithFormat:@"llWebBridge.getRequestMessage()"]];
    if ([msg isEqual:@""]) {
        msg = @"{}";
    }
    
    NSDictionary *msgDic = [msg getObjectFromJSONStringWithOption:NSJSONReadingAllowFragments];
    return msgDic;
}

- (NSString *)callBackStringFrom:(id)obj idStr:(NSString *)idStr isSuccess:(BOOL)isSuccess{
    NSDictionary *dictionary = [NSDictionary dictionaryWithObjectsAndKeys:
                                isSuccess?@"0":@"1",@"status",
                                obj,@"message",nil];
    NSString *jsonStr = [dictionary getJSONStringFromObject];
    jsonStr = [jsonStr stringByReplacingOccurrencesOfString:@"\'" withString:@"\\'"];
    NSString *script = [NSString stringWithFormat:@"llWebBridge.callBackJs('%@','%@');", jsonStr, idStr];
    return script;
}

- (NSString *)doJavaScriptString:(NSString *)script{
    return [[self webView] stringByEvaluatingJavaScriptFromString:script];
}

@end
