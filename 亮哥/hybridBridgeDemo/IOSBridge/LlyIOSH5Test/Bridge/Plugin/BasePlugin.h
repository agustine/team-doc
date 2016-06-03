//
//  BasePlugin.h
//  LlyIOSH5Test
//
//  Created by 邓志亮 on 16/5/14.
//  Copyright © 2016年 邓志亮. All rights reserved.
//

#import <UIKit/UIKit.h>



@interface BasePlugin : NSObject

@property(nonatomic, strong) UIViewController* viewCtrl;
@property(nonatomic, strong) NSDictionary* params;


//构造函数
-(id)initWithParams:(UIViewController*)viewCtrl params:(NSDictionary*) params;



@end
