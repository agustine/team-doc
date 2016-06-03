//
//  BPJSONTool.m
//  BestpayLogin
//
//  Created by yfzx_sh_louwk on 15/9/1.
//  Copyright (c) 2015年 Lisworking. All rights reserved.
//

#import "LLJSONTool.h"

@implementation LLJSONTool

@end

#pragma mark - Category
#pragma mark - 解码

#pragma mark - NSString

@implementation NSString (LLJSONDeSerialization)

- (id)getObjectFromJSONString{
    NSData *data = [self dataUsingEncoding:NSUTF8StringEncoding];
    NSError *error = nil;
    id object = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingAllowFragments error:&error];
    if (error != nil)
        return nil;
    return object;
}

- (id)getObjectFromJSONStringWithOption:(NSJSONReadingOptions)option{
    NSData *data = [self dataUsingEncoding:NSUTF8StringEncoding];
    NSError *error = nil;
    id object = [NSJSONSerialization JSONObjectWithData:data options:option error:&error];
    if (error) {
        return nil;
    }
    else {
        return object;
    }
}


@end

#pragma mark - NSData

@implementation NSData (LLJSONDeSerialization)

- (id)getObjectFromJSONData{
    NSError *error = nil;
    id object = [NSJSONSerialization JSONObjectWithData:self options:NSJSONReadingAllowFragments error:&error];
    if (error != nil) {
        return nil;
    }
    else {
        return object;
    }
}

@end

#pragma mark - 编码

#pragma mark - NSObject

@implementation NSObject (LLJSONSerialization)

- (NSData *)getJSONDataFromObject{
    if (![NSJSONSerialization isValidJSONObject:self]) {
        return nil;
    }
    
    NSError *error = nil;
    NSData *data = [NSJSONSerialization dataWithJSONObject:self options:kNilOptions error:&error];
    if (error != nil){
        return nil;
    }
    else {
        return data;
    }
}

- (NSString *)getJSONStringFromObject{
    if (![NSJSONSerialization isValidJSONObject:self]) {
        return nil;
    }
    
    NSError *error = nil;
    NSData *data = [NSJSONSerialization dataWithJSONObject:self options:kNilOptions error:&error];
    if (error != nil) {
        return nil;
    }
    else {
        NSString *string = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
        return string;
    }
}

@end