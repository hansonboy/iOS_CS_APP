//
//  CoreDataTools.h
//  Client
//
//  Created by libmooc on 15/7/14.
//  Copyright (c) 2015年 hansonboy. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface CoreDataTools : NSObject
@property (strong,nonatomic) UIManagedDocument * document;
+(CoreDataTools*)sharedCoreDataTools;
+(void)exampleTest;
@end
