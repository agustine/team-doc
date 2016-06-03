//
//  ViewController.h
//  LlyIOSH5Test
//
//  Created by 邓志亮 on 16/5/10.
//  Copyright © 2016年 邓志亮. All rights reserved.
//

#import <UIKit/UIKit.h>

#define SuppressPerformSelectorLeakWarning(Stuff) \
do { \
_Pragma("clang diagnostic push") \
_Pragma("clang diagnostic ignored \"-Warc-performSelector-leaks\"") \
Stuff; \
_Pragma("clang diagnostic pop") \
} while (0)


@interface H5ViewController : UIViewController


@end

