//
//  CoreDataTools.m
//  Client
//
//  Created by libmooc on 15/7/14.
//  Copyright (c) 2015年 hansonboy. All rights reserved.
//

#import "CoreDataTools.h"
#import "Person.h"
#define DDLog(xx, ...)  NSLog(@"%s(%d): " xx, __PRETTY_FUNCTION__, __LINE__, ##__VA_ARGS__)
@implementation CoreDataTools
#pragma -mark Singleton Instance
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
        NSFileManager * fileManager = [NSFileManager defaultManager];
        NSURL * documentsDirectory = [[fileManager URLsForDirectory:NSDocumentDirectory inDomains:NSUserDomainMask]firstObject];
        //    NSString * documentName = @"MyDocument";
        NSURL * url = [documentsDirectory URLByAppendingPathComponent:@"MyDocument"];
        _document = [[UIManagedDocument alloc] initWithFileURL:url];
    }
    return self;
}

+(id)allocWithZone:(NSZone *)zone{
    return [self sharedCoreDataTools];
}
-(id)copyWithZone:(NSZone*)zone{
    return self;
}

#pragma mark Using UIMangedDocument to CoreData
-(void)openDocumentWithCompletionHandler:(void(^)(BOOL success))completionHandler{
    NSURL *url = self.document.fileURL;
    BOOL fileExists = [[NSFileManager defaultManager]fileExistsAtPath:[url path]];
    if (fileExists) {
        [self.document openWithCompletionHandler:^(BOOL success) {
            completionHandler(success);
        }];
    }else{
        [self.document saveToURL:url forSaveOperation:UIDocumentSaveForCreating completionHandler:^(BOOL success) {
            completionHandler(success);
        }];
    }
}
-(void)saveDocumentWithCompletionHandler:(void(^)(BOOL success))completionHandler{
    [self.document saveToURL:self.document.fileURL forSaveOperation:UIDocumentSaveForOverwriting completionHandler:^(BOOL success) {
        completionHandler(success);
    }];
}
-(void)closeDocumentWithCompletionHandler:(void(^)(BOOL success))completionHandler{
    [self.document closeWithCompletionHandler:^(BOOL success) {
        completionHandler(success);
    }];

}

+(void)documentIsReady{
    if([CoreDataTools sharedCoreDataTools].document.documentState == UIDocumentStateNormal){
        //        starting using document
        NSManagedObjectContext * context = [CoreDataTools sharedCoreDataTools].document.managedObjectContext;
        //        starting doing core data stuff with context
        Person * person = [NSEntityDescription insertNewObjectForEntityForName:@"Person" inManagedObjectContext:context];
        //        inserting objects into the database
        // update by kvc or property
//        [person setValue:@"wangjiaweni" forKey:@"name"];
        person.id1 = @1001;
        person.name = @"wangjianwei";
        person.age = @23;
        //      Querying
        NSFetchRequest * request = [NSFetchRequest fetchRequestWithEntityName:@"Person"];
        request.fetchBatchSize = 20;
        request.fetchLimit = 100;
        NSSortDescriptor * sortDescriptor = [NSSortDescriptor sortDescriptorWithKey:@"name" ascending:YES selector:@selector(localizedStandardCompare:)];
        request.sortDescriptors = @[sortDescriptor];
        
        NSPredicate * predicate1 = [NSPredicate predicateWithFormat:@"id1 = %@",@1001];
        NSPredicate * predicate2 = [NSPredicate predicateWithFormat:@"name = %@",@"wangjianwei"];
        NSPredicate * predicate3= [NSCompoundPredicate andPredicateWithSubpredicates:@[predicate1,predicate2]];
        NSPredicate * predicate4 = [NSPredicate predicateWithFormat:@"(id1 = %@)AND(name = %@)",@1001,@"wangjianwei"];
        request.predicate = predicate4;
        NSError * error;
        NSArray * persons = [context executeFetchRequest:request error:&error];
        DDLog(@"Persons:%@",persons);
        
        //Delete
         NSEntityDescription * entity = [NSEntityDescription entityForName:@"Person" inManagedObjectContext:context];
        NSFetchRequest * req = [[NSFetchRequest alloc]init];
        [req setEntity:entity];
        [req setPredicate:predicate1];
        [req setIncludesPropertyValues:NO];
        NSArray * datas = [context executeFetchRequest:req error:&error];
        if(!error&& datas &&[datas count]){
            for (NSManagedObject * obj in datas) {
                [context deleteObject:obj];
            }
            if (![context save:&error]) {
                DDLog(@"%@",error);
            }
        }
    }
}
#pragma  mark ExampleTest
+(void)exampleTest{
    CoreDataTools * CD1 = [CoreDataTools sharedCoreDataTools];
    CoreDataTools * CD2 = [[CoreDataTools alloc]init];
    CoreDataTools * CD3 = [CoreDataTools sharedCoreDataTools];
    NSLog(@"%@ == %@ == %@",CD1,CD2,CD3);
    [CD1 openDocumentWithCompletionHandler:^(BOOL success) {
        [CoreDataTools documentIsReady];
    }];
    [CD1 saveDocumentWithCompletionHandler:^(BOOL success) {
        if (success) {
             DDLog(@"save success");
        }
    }];
    [CD1 closeDocumentWithCompletionHandler:^(BOOL success) {
        if (success) {
            DDLog(@"close success");
        }
    }];
}
@end
