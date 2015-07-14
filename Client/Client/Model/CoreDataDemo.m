//
//  CoreDataDemo.m
//  Client
//
//  Created by libmooc on 15/7/14.
//  Copyright (c) 2015年 hansonboy. All rights reserved.
//

#import "CoreDataDemo.h"
#import <UIKit/UIKit.h>
#import <CoreData/CoreData.h>
#import "Person.h"
@implementation CoreDataDemo
-(void)createUIManagedDocument{
    //create UIMangedDoucment
    NSFileManager * fileManager = [NSFileManager defaultManager];
    NSURL * documentsDirectory = [[fileManager URLsForDirectory:NSDocumentationDirectory inDomains:NSUserDomainMask]firstObject];
    NSString * documentName = @"MyDocument";
    NSURL * url = [documentsDirectory URLByAppendingPathComponent:documentName];
    self.document = [[UIManagedDocument alloc]initWithFileURL:url];
    //open or create UImangedDocument
    BOOL fileExists = [[NSFileManager defaultManager]fileExistsAtPath:[url path]];
    if (fileExists) {
        [self.document openWithCompletionHandler:^(BOOL success) {
//           block to execute when open
            if (success) {
                [self documentIsReady];
            }else NSLog(@"Couldn't open document at %@",url);
        }];
    }else{
        [self.document saveToURL:url forSaveOperation:UIDocumentSaveForCreating completionHandler:^(BOOL success) {
// block to execute when create is done
            if (success) {
                [self documentIsReady];
            }else NSLog(@"couldn't open document at %@",url);
        }];
    }
//   saving the document
    [self.document saveToURL:self.document.fileURL forSaveOperation:UIDocumentSaveForOverwriting completionHandler:^(BOOL success) {
//        block to execute when save is done
        
    }];
//    closing the document
    [self.document closeWithCompletionHandler:^(BOOL success) {
        if(!success) NSLog(@"failed to close  doucment %@",self.document.localizedName);
    }];
}
-(void)documentIsReady{
    if(self.document.documentState == UIDocumentStateNormal){
//        starting using document
        NSManagedObjectContext * context = self.document.managedObjectContext;
//        starting doing core data stuff with context
        
//        inserting objects into the database
        Person * person = [NSEntityDescription insertNewObjectForEntityForName:@"Person" inManagedObjectContext:context];
        // update by kvc or property
        [person setValue:@"wangjiaweni" forKey:@"name"];
        person.name = @"wangjiawnei";
        //Delete
        [self.document.managedObjectContext deleteObject:person];
//      Querying
        NSFetchRequest * request = [NSFetchRequest fetchRequestWithEntityName:@"Person"];
        request.fetchBatchSize = 20;
        request.fetchLimit = 100;
        NSSortDescriptor * sortDescriptor = [NSSortDescriptor sortDescriptorWithKey:@"name" ascending:YES selector:@selector(localizedStandardCompare:)];
        request.sortDescriptors = @[sortDescriptor];
        request.predicate;
        
    }
}

@end
