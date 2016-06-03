//
//  BasePlugin.m
//  LlyIOSH5Test
//
//  Created by 邓志亮 on 16/5/14.
//  Copyright © 2016年 邓志亮. All rights reserved.
//

#import "BasePlugin.h"

@implementation BasePlugin

-(id)initWithParams:(UIViewController*)viewCtrl params:(NSDictionary*) params{
    self = [super init];
    if(self != nil){
        _viewCtrl = viewCtrl;
        _params = params;
    }
    return self;
}

@end
