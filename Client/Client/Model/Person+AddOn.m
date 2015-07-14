//
//  Person+AddOn.m
//  Client
//
//  Created by libmooc on 15/7/14.
//  Copyright (c) 2015年 hansonboy. All rights reserved.
//

#import "Person+AddOn.h"

@implementation Person (AddOn)
-(instancetype)initWithJsonObject:(id)object{
    self = [super init];
    if(self){
        self.id1 = [(NSDictionary*)object objectForKey:@"id"];
        self.age = [(NSDictionary*)object objectForKey:@"age"];
        self.name = [(NSDictionary*)object objectForKey:@"name"];
    }
    return self;
}
@end
