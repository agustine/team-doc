//
//  BPJSONTool.h
//  BestpayLogin
//
//  Created by yfzx_sh_louwk on 15/9/1.
//  Copyright (c) 2015年 Lisworking. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface LLJSONTool : NSObject

@end

#pragma mark - Category
#pragma mark - 解码

#pragma mark - NSString

@interface NSString (LLJSONDeSerialization)

- (id)getObjectFromJSONString;
- (id)getObjectFromJSONStringWithOption:(NSJSONReadingOptions)option;

@end

#pragma mark - NSData

@interface NSData (LLJSONDeSerialization)

- (id)getObjectFromJSONData;

@end

#pragma mark - 编码

#pragma mark - NSObject

@interface NSObject (LLJSONSerialization)

- (NSData *)getJSONDataFromObject;
- (NSString *)getJSONStringFromObject;

@end