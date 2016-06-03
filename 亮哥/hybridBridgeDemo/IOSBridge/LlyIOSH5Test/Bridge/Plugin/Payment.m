//
//  Payment.m
//  LlyIOSH5Test
//
//  Created by 邓志亮 on 16/5/13.
//  Copyright © 2016年 邓志亮. All rights reserved.
//

#import "Payment.h"
#import "PaymentViewController.h"

@implementation Payment


-(void) pay{
    PaymentViewController *pc = [[PaymentViewController alloc]init];
    pc.payParams = [self params];
    [[self viewCtrl].navigationController pushViewController:pc animated:YES];
}


@end
