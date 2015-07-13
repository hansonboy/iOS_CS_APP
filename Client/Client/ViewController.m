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
    NSString * urlStr = @"http://192.168.1.109:8080/iOS_HttpServer_MySQL_Tomcat_JSON/HttpServer?id=1001";
    NSURL * url = [NSURL URLWithString:urlStr];
    NSURLRequest * request = [NSURLRequest requestWithURL:url];
    NSData * data = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    NSLog(@"-----%d-------",data.length);
    NSString *rcvData = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
    self.helloworld.text = rcvData;
    NSLog(@"%@",rcvData);
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
