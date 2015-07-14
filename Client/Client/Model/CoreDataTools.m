//
//  CoreDataTools.m
//  Client
//
//  Created by libmooc on 15/7/14.
//  Copyright (c) 2015年 hansonboy. All rights reserved.
//

#import "CoreDataTools.h"

@implementation CoreDataTools
+(CoreDataTools *)sharedCoreDataTools{
    static CoreDataTools * sharedCoreDataTools;
    static dispatch_once_t predicate = 0;
    dispatch_once(&predicate,^{
        sharedCoreDataTools = [[super allocWithZone:NULL]init];
    });
    return sharedCoreDataTools;
}
-(id)init{
    self = [super init];
    if(self){
        
    }
    return self;
}

+(id)allocWithZone:(NSZone *)zone{
    return [self sharedCoreDataTools];
}
-(id)copyWithZone:(NSZone*)zone{
    return self;
}
+(void)exampleTest{
    CoreDataTools * CD1 = [CoreDataTools sharedCoreDataTools];
    CoreDataTools * CD2 = [[CoreDataTools alloc]init];
    CoreDataTools * CD3 = [CoreDataTools sharedCoreDataTools];
    NSLog(@"%@ == %@ == %@",CD1,CD2,CD3);
}
@end
