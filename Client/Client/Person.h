//
//  Person.h
//  Client
//
//  Created by libmooc on 15/7/14.
//  Copyright (c) 2015年 hansonboy. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>


@interface Person : NSManagedObject

@property (nonatomic, retain) NSNumber * id1;
@property (nonatomic, retain) NSString * name;
@property (nonatomic, retain) NSNumber * age;

@end
