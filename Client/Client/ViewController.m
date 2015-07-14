//
//  ViewController.m
//  Client
//
//  Created by libmooc on 15/7/12.
//  Copyright (c) 2015年 hansonboy. All rights reserved.
//

#import "ViewController.h"
#define ScreenBounds ([UIScreen mainScreen].bounds)
#define NextYFor(a)   (a.frame.origin.y+a.frame.size.height+10)
@interface ViewController ()
@property (strong,nonatomic) UILabel * helloworld;
@property (strong,nonatomic) UIButton * sendButton;
@end
@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.

    self.sendButton = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    self.sendButton.frame = CGRectMake(self.view.center.x-ScreenBounds.size.width/4, 64, ScreenBounds.size.width/2, 30);
    self.sendButton.backgroundColor = [UIColor blackColor];
    self.sendButton.alpha = 0.5;
//    self.sendButton.titleLabel.textColor = [UIColor whiteColor];
    [self.sendButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.sendButton setTitle:@"请求数据" forState:UIControlStateNormal];
    [self.sendButton.titleLabel setTextAlignment:NSTextAlignmentCenter];
    [self.sendButton addTarget:self action:@selector(sendHttps:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:self.sendButton];
    
    self.helloworld = [[UILabel alloc]initWithFrame:CGRectMake(10, NextYFor(self.sendButton), 320, 40)];
    self.helloworld.textAlignment = NSTextAlignmentCenter;
    self.helloworld.text = @"我将显示d服务器数据";
    [self.view addSubview:self.helloworld];
    
}
-(void)sendHttps:(UIButton*)sender{
    NSString * urlStr = @"http://113.54.158.225:8080/iOS_HttpServer_MySQL_Tomcat_JSON/HttpServer?id=1001";
    NSURL * url = [NSURL URLWithString:urlStr];
    NSURLRequest * request = [NSURLRequest requestWithURL:url];
    NSData * data = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    NSLog(@"-----%d-------",data.length);
    NSString *rcvData = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
    self.helloworld.text = rcvData;
    NSLog(@"%@",rcvData);
    
    NSError * __autoreleasing error;
    NSDictionary * resultJson = [[NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error] objectForKey:@"Person"];
    if (resultJson == nil) {
        NSLog(@"JSON 解析失败");
    }
    NSLog(@"id = %@,name = %@,age = %@",[resultJson objectForKey:@"id"],[resultJson objectForKey:@"name"],[resultJson objectForKey:@"age"]);
    
    NSDictionary * dic = [NSDictionary dictionaryWithObjectsAndKeys:@"1001",@"id",@"wangjiawnei",@"name",nil];
//    if([NSJSONSerialization isValidJSONObject:dic]){
//        NSError *error;
//        NSData * resultData = [NSJSONSerialization dataWithJSONObject:dic options:NSJSONWritingPrettyPrinted error:&error];
//        NSLog(@"Json data:%@",[[NSString alloc]initWithData:resultData encoding:NSUTF8StringEncoding]);
//    }
    NSString *jsonString = [self jsonString:[self JSONData:dic]];
    NSLog(@"jsonString:%@",jsonString);
    NSLog(@"jsonObject:%@",[self JSONObject:[self jsonData:jsonString]]);
    
    
    
}
//将NSDictionary 和 NSArray 转化为 JSON data
-(NSData*)JSONData:(id)object{
    NSError * error;
    if ([NSJSONSerialization isValidJSONObject:object]) {
        NSData * jsonData = [NSJSONSerialization dataWithJSONObject:object options:NSJSONWritingPrettyPrinted error:&error];
        if([jsonData length]>0 && error == nil){
            return jsonData;
        }else{
            return nil;
        }
    }else return nil;
}
-(NSString*)jsonString:(NSData*)jsonData{
    return [[NSString alloc]initWithData:jsonData encoding:NSUTF8StringEncoding];
}
//将JSonData 转化为NSArray 或者 NSDictionary
-(NSData*)jsonData:(NSString*)jsonString{
    return [jsonString dataUsingEncoding:NSASCIIStringEncoding];
}
-(id)JSONObject:(NSData*)jsonData{
    NSError*error = nil;
    id jsonObject = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONReadingAllowFragments error:&error];
    if(jsonObject != nil&& error ==nil) {
        return jsonObject;
    }else{
        //解析错误
        return nil;
    }
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
