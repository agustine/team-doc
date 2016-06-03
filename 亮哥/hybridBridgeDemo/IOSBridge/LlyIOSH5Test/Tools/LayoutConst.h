//
//  LayoutConst.h
//  LlyIOSH5Test
//
//  Created by 邓志亮 on 16/5/16.
//  Copyright © 2016年 邓志亮. All rights reserved.
//

#import <Foundation/Foundation.h>

#define _iOS7_Or_Later_ (([[UIDevice currentDevice] systemVersion].floatValue >= 7.0f) ? YES : NO)
#define NavgationBarHeight ((_iOS7_Or_Later_) ? 64.0 : 44.0)

//屏幕尺寸
#define ScreenWidth ([UIScreen mainScreen].bounds.size.width) //屏幕宽度
#define ScreenHeight ([UIScreen mainScreen].bounds.size.height) //屏幕高度

@interface LayoutConst : NSObject

@end
