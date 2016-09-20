    //HI/** This is a sample code for your bot**/
    
    
    	    function MessageHandler(context, event) {
           
            var userArray=context.simpledb.botleveldata.userArray;
          	var channel = event.contextobj.channeltype;
            var userid = event.sender;
            
    ///////////////////////////////////////////////needs to be set
            var apikey='6af25697f1a04161c19c35ad95e01b76';
    //////////////////////////////////////////////
            
    
    
            var state = context.simpledb.botleveldata["state_"+event.sender];
            if(!userArray)
            {
               userArray=[];
            }
           
           
            var msg = event.message;
            var user={
                name:'',
                userid:'',
                password:0,
                word:'',
                playingwith:'',
                contextobj:'',
                status:'offline',
                botname:'',
                waiting:false
            }; 
             var link;
    	        if(msg.toLowerCase()==='hi' || msg.toLowerCase()==='hello'|| msg =="/start" || msg == "startchattingevent")
    	        {
    	   			
    	   			
    	   	//context.sendResponse('s'+check_user(userArray,event.contextobj));	         
    	        if(!check_user(event.contextobj))
    	         {
    	             
                            
    
    	            var password = Math.random()*10000;
                     
    	            
    	           while(parseInt(password)<=999)
    	            {
    	            password = Math.random()*10000;
                     
    	                
    	            }
    	            
    	            
    	              password=parseInt(password); 
    	            var boolean = check_password(userArray,password);
    	            while(boolean)
    	            {
    	               boolean = check_password(userArray,password);
    
    	   			}
    
    	   			user.userid=''+event.sender;
    	   			user.password=password;
    	   			user.name=event.senderobj.display;
    	   			user.playingwith='';
    	   			user.contextobj=event.contextobj;
    	   			user.status='offline';
    	   			user.botname=event.botname;
    	   			userArray.push(user);
    
                    context.simpledb.botleveldata.userArray=userArray;
                   
    
                    	
    	             link=getLink(channel);
    
                    context.simpledb.saveData();
    	   			context.sendResponse('Hi, this is the jumblewithfriends bot. You and a friend take turns sending jumbled words to each other. Un-jumble the word, if you can, to see the coded message. It\'s a great game to play with friends.\n\nTo start, either you enter your friend\'s passcode or ask the friend to enter yours.  \n\nSay \'Play <passcode>\'to start game.\n\nSay \'Stop\' to exit game at any time\n\nTo invite your friend to play, just forward this message to them: '+'\n\nLink -->'+link+'\n\n'+user.name+'\'s passcode-->'+user.password);	
    	        
    	             
    	             
    	         }
    	         else
    	         {
    	             user = getUserById(event.sender);
    	            
    	             
    	             
    	             
    	             link=getLink(channel);
    	             context.sendResponse('Hi, this is the jumblewithfriends bot. You and a friend take turns sending jumbled words to each other. Un-jumble the word, if you can, to see the coded message. It\'s a great game to play with friends.\n\nTo start, either you enter your friend\'s passcode or ask the friend to enter yours.  \n\nSay \'Play <passcode>\'to start game.\n\nSay \'Stop\' to exit game at any time\n\nTo invite your friend to play, just forward this message to them: '+'\n\nLink -->'+link+'\n\n'+user.name+'\'s passcode-->'+user.password);	
    	             
    	             
    	         }
    	    }
    	   else if(msg.toLowerCase()==='start')
    	   {
    	         if(!check_user(event.contextobj))
    	         {
                            
    
    	            var password = Math.random()*10000;
                     
    	            
    	       while(parseInt(password)<=999)
    	            {
    	            password = Math.random()*10000;
                     
    	                
    	            }
    	            
    	            
    	              password=parseInt(password); 
    	            var boolean = check_password(userArray,password);
    	            while(boolean)
    	            {
    	               boolean = check_password(userArray,password);
    
    	   			}
    
    	   			user.userid=''+event.sender;
    	   			user.password=password;
    	   			user.name=event.senderobj.display;
    	   			user.playingwith='';
    	   			user.contextobj=event.contextobj;
    	   			user.status='offline';
    	   			user.botname=event.botname;
    	   			userArray.push(user);
    
                    context.simpledb.botleveldata.userArray=userArray;
                   
    
                    	
    	             link=getLink(channel);
    
                    context.simpledb.saveData();
    	   			context.sendResponse('To start, either you enter your friend\'s passcode or ask the friend to enter yours.  \n\nSay \'Play <passcode>\'to start game.\n\nSay \'Stop\' to exit game at any time\n\nTo invite your friend to play, just forward this message to them: '+'\n\nLink -->'+link+'\n\n'+user.name+'\'s passcode-->'+user.password);	
    	        
    	             
    	             
    	         }
    	         else
    	         {
    	             user = getUserById(event.sender);
    	            
    	             
    	             
    	             
    	             link=getLink(channel);
    	             context.sendResponse('To start, either you enter your friend\'s passcode or ask the friend to enter yours.  \n\nSay \'Play <passcode>\'to start game.\n\nSay \'Stop\' to exit game at any time\n\nTo invite your friend to play, just forward this message to them: '+'\n\nLink -->'+link+'\n\n'+user.name+'\'s passcode-->'+user.password);	
    	             
    	             
    	         }
    	       
    	   }
    	   else if(msg.toLowerCase().indexOf('play ')===0)
    	        {
    	            
    	        	var passcode = msg.substr(5,msg.length);
    	        //	context.sendResponse('passcode '+passcode);
    
                    
                    
                    
                if(passcode.length===4)
                {
                    
               
    	            if(check_user(event.contextobj))
    	        	{
    	        	    var opponent = getOpponent(passcode);
    	        
    
    
    	       // context.sendResponse('x'+user.status+"y"+opponent.status);
    	        
    
    	        if(user.status!='online' && opponent.status!='online')
                 {
                            	
    
                            	
    	        	
    	        	user = getUserById(userid);
    	        	if(!opponent)
    	        	{
    
    	        		context.sendResponse('Sorry, wrong passcode ..!');
    	        	}
    	        	else
    	        	{
    	        		
    	        		var position_oppoent = userArray.indexOf(opponent);
    	        		    
    	        		if(opponent.status==='online' || user.status==='online')
    	        		{
    	        		    if(opponent.password===user.password)
    	        		    {
    	        		        	context.sendResponse('You can not play with yourself :P');
    	        		    }
    	        		   else
    	        		    {
    	        			context.sendResponse('Sorry buddy he is currently not available try again after sometime.');
    	        		
    	        		    }
    	        		}
    	        		else
    	        		{
    	        			var position_user = userArray.indexOf(user);
    	        		    user = getUserById(userid);
    	        		    if(!(opponent.password==user.password))
    	        		    {
    	        		    opponent.playingwith=event.contextobj;
    	        			opponent.status='online';
    	        		    userArray[position_oppoent]=opponent; 
    
    					    user.playingwith=opponent.contextobj;
    						user.status='online';
    	        	      	userArray[position_user]=user;
    	        	      	//has joined your game. Please share a word and I will mix it up for you
    	        	      	
    	
    	        	      	var message = user.name+' has joined your game.\nPlease share a word or sentence and I will mix it up for you';
    						
    	        	        context.simpledb.botleveldata.userArray=userArray;			
    	        	        context.simpledb.botleveldata["hint_"+event.sender]=1;
    
    						SendMessage(opponent.botname,opponent.contextobj,apikey,message);
                            SendMessage(user.botname,user.contextobj,apikey,'Ok, please wait till '+opponent.name+' sends you a challenge.');
    						context.simpledb.botleveldata["state_"+event.sender]='answer';
    						context.simpledb.botleveldata["state_"+opponent.userid]='question';
    						 context.simpledb.saveData();
    						//context.sendResponse('Ok');
     					//	context.sendResponse('Ok , wait till '+opponent.name+' gives you challenge');
    	        			}
    	        			else
    	        			{
    	        				context.sendResponse('You cant play with yourself :P');
    	        			}
    	        	     
    
    	        	           }
    	     	            }
                         
                            }
                            else
                            {
                              context.sendResponse('Please stop ongoing game before starting new game');  
                            }
    
    	        	 }//eof check user
    	            else
    	        	 {
    	        	 	     addUser(event);
                            
    	        	var opponent = getOpponent(msg);
    	        	user = getUserById(userid);
    	        	if(!opponent)
    	        	{
    
    	        		context.sendResponse('Sorry no user with this passcode exists');
    	        	}
    	        	else
    	        	{
    	        		
    	        		var position_oppoent = userArray.indexOf(opponent);
    	        		    
    	        		if(opponent.status==='online')
    	        		{
    	        			context.sendResponse('Sorry buddy he is currently not available try again after sometime.');
    	        		}
    	        		else
    	        		{
    	        			var position_user = userArray.indexOf(user);
    	        		    user = getUserById(userid);
    	        		    if(!(opponent.password==user.password))
    	        		    {
    	        		    opponent.playingwith=event.contextobj;
    	        			opponent.status='online';
    	        		    userArray[position_oppoent]=opponent; 
    
    					    user.playingwith=opponent.contextobj;
    						user.status='online';
    	        	      	userArray[position_user]=user;
    	        	      	//has joined your game. Please share a word and I will mix it up for you
    	        	      	
    	
    	        	      	var message = user.name+' has joined your game.\nPlease share a word or sentence and I will mix it up for you';
    						
    	        	        context.simpledb.botleveldata.userArray=userArray;	
    
    						SendMessage(opponent.botname,opponent.contextobj,apikey,message);
                            SendMessage(user.botname,user.contextobj,apikey,'Ok, please wait till '+opponent.name+' sends you a challenge.');
    						context.simpledb.botleveldata["state_"+event.sender]='answer';
    						context.simpledb.botleveldata["state_"+opponent.userid]='question';
    						 context.simpledb.saveData();
    						//context.sendResponse('Ok');
     					//	context.sendResponse('Ok , wait till '+opponent.name+' gives you challenge');
    	        			}
    	        			else
    	        			{
    	        				context.sendResponse('You can not play with yourself :P');
    	        			}
    	        	     
    
    	                        }
    	            	}    
    
    	        		 
    	        		 
    	           }//eof 
    
    	       
                }//eof valid passcode
                else
                {
                    	context.sendResponse('Please enter valid passcode');
                }  
    	        		
    	        }else if(msg.toLowerCase()==='hint')
    	        {
    	        	var opponent = getOpponentByContext(event.contextobj);
    	        	user = getUserById(userid);
    	        	if(opponent && 	state==='answer')
    	           {
    	            
    	           	var word = 	context.simpledb.botleveldata["word_"+opponent.userid];
    	        	var hint_count = context.simpledb.botleveldata["hint_"+event.sender];
    	        	var wordlength = parseInt(word.length);
    	        	if(word)
    	        	{
    	        		 if(hint_count<=3)
    	        		 {
    
    	        			var scramble1 = getSentenceHint(word,hint_count);
    	        			hint_count++;			
    	        			context.simpledb.botleveldata["hint_"+event.sender]=hint_count;      
    	        			context.sendResponse('Ok, here\'s a hint. \n'+scramble1);
    
    	        		}
    	        		else
    	        		{
    	        			context.sendResponse('Sorry, you can only get 3 hints. Say \'pass\' if you want to give up.');
    	        		}
    	        	}
    	        	else
    	        	{
    	        		context.sendResponse('User has not provided any challenge yet');
    	        	}
    	           	
    
    
    
    
    	           }
    	        	else
    	        	{
    	        		
    	        		context.sendResponse('You have not initiated any games yet');
    
    	        	}
    	        }
    	        else if( msg.toLowerCase()==='pass')
    	        {
    	          
    
                    user = getUserById(event.sender);
    	            
    	            var position_user = userArray.indexOf(user);
    	        	var position_oppoent = userArray.indexOf(opponent);
    	            
    	            var opponent=getOpponentByContext(event.contextobj);
    	           if(opponent && 	state==='answer')
    	           {
    	            
    	        		SendMessage(opponent.botname,opponent.contextobj,apikey,user.name+' has passed \nWait for challenge from '+user.name);
    	        		
    	        	    var word = 	context.simpledb.botleveldata["word_"+opponent.userid];
    
    	        		opponent.waiting=true;
    
    	        		user.waiting=false;
    	        		userArray[position_user]=user;
    	        	    userArray[position_oppoent]=opponent;
    	        	    context.simpledb.botleveldata.userArray=userArray;
    	        		context.simpledb.botleveldata["state_"+event.sender]='question';
    					context.simpledb.botleveldata["state_"+opponent.userid]='answer';
    					context.simpledb.botleveldata["word_"+opponent.userid]='';
    	        		context.simpledb.botleveldata["word_"+event.userid]='';
    	        	    context.simpledb.botleveldata["hint_"+event.sender]=1;
        				context.simpledb.botleveldata["hint_"+opponent.userid]=1;
    
    	        		 context.simpledb.saveData();
    	        		
    	        		SendMessage(user.botname,user.contextobj,apikey,'Ok, '+user.name+' the challenge was : '+word+'\nNow, it\'s your turn to provide a challenge for '+opponent.name); 
                   		 
    	        
    	            
    	            }
    	            else
    	            {
    	                if(!opponent)
    	                {
    	                    context.sendResponse('You have not intiated any games yet');
    	                }
    	                else
    	                {
    	               	    context.sendResponse('You can pass only when challenged');   
    	                }
    
    
    
    	        }
    	            
                }else if( msg.toLowerCase()==='stop')
    	        {
    	            
    	            var opponent=getOpponentByContext(event.contextobj);
    	        	 
                   	    
    	        	user = getUserById(event.sender);
    	            
    	            var position_user = userArray.indexOf(user);
    	        	var position_oppoent = userArray.indexOf(opponent);
    	            
    	            
    	            
    	            if(opponent && user)
    	            {
    	                user.status='offline';
    	                user.waiting=false;
    	                user.playingwith='';
    	                opponent.status='offline';
    	                opponent.waiting=false;
    	                opponent.playingwith='';
                     	userArray[position_user]=user;
                        userArray[position_oppoent]=opponent;
                        context.simpledb.botleveldata.userArray=userArray;
                        context.simpledb.botleveldata["state_"+event.sender]='';
        				context.simpledb.botleveldata["state_"+opponent.userid]='';
        				context.simpledb.botleveldata["hint_"+event.sender]=1;
        				context.simpledb.botleveldata["hint_"+opponent.userid]=1;
        				var message = ''+user.name+' has left the game ....';
        				SendMessage(opponent.botname,opponent.contextobj,apikey,message);
        				SendMessage(user.botname,user.contextobj,apikey,'Ok, come back soon');
        				 context.simpledb.saveData();
    	            }
    	            else
    	            {
    	                
    	                context.sendResponse('You have not initiated any game');
    	            }
    	            
    	            
    	            
                }else if(msg.toLowerCase()==='help')
    	        {
                    context.sendResponse('Hi, this is the jumblewithfriends bot. You and a friend take turns sending jumbled words to each other. Un-jumble the word, if you can, to see the coded message. It\'s a great game to play with friends.\nSay \'hi/hello\' to get your passcode. \nSay \'Play <passcode>\'to start game.\nSay \'Stop\' to exit game at any time');
    	        
    	            
    	            
    	        }else if(msg.toLowerCase()==='cleardata')
    	        {
    	               context.simpledb.botleveldata={};
    	               userArray=[];
    	               context.simpledb.botleveldata.userArray=userArray;
    	               context.sendResponse('Data cleared');
                }
                else if(state==='question')
    	        {
    	            var opponent=getOpponentByContext(event.contextobj);
    	        	 //    context.sendResponse('x'+opponent);
                   	    
    	        	user = getUserById(event.sender);
    	        	var word = 	context.simpledb.botleveldata["word_"+opponent.userid];
    	        	var position_user = userArray.indexOf(user);
    	        	var position_oppoent = userArray.indexOf(opponent);
    	        	
    	            
    
    	        	if(user.waiting)
    	        	{
    	        		context.sendResponse('Wait till user completes first challenge or passes it ');
    	        	}
    	        	else
    	        	{
                   	    
                        if(msg.length>3)
                        {
                            
                        
    	        		var res = msg.split(' ');
                        var ScrambledWord='';    
                        if(res.length>1)
                        {
                          for(var i= 0;i<res.length;i++)
                          {
                           ScrambledWord+=' '+scramble(res[i]);
                          }	
                    	}
                    	else
                    	{
                    		 ScrambledWord =  scramble(msg);
                    	}
    
                   	   
                   	    
    
    
                   	    opponent.waiting=true;
    	        	    userArray[position_oppoent]=opponent;
    	        		context.simpledb.botleveldata["state_"+event.sender]='question';
    					context.simpledb.botleveldata["state_"+opponent.userid]='answer';
    	        		context.simpledb.botleveldata["hint_"+event.sender]=1;
        				context.simpledb.botleveldata["hint_"+opponent.userid]=1;
    	        		
    					
    
    	        	user.waiting=true;
    	        	userArray[position_user]=user;
    	        	context.simpledb.botleveldata["word_"+event.sender]=msg;	
    	        	 context.simpledb.botleveldata.userArray=userArray;	
    	        	
    	        	SendMessage(opponent.botname,opponent.contextobj,apikey,user.name+' has sent you the following challenge : '+'\n'+ScrambledWord);
    	        	SendMessage(user.botname,user.contextobj,apikey,'Ok, I have sent following challenge to '+opponent.name+'\n '+ScrambledWord);
    	        	 context.simpledb.saveData();
    	        	//context.sendResponse('Ok');
    	        	
                        }
                        else
                        {
                            context.sendResponse('Challenge should contain atleast 4 characters.');
                        }
    	        	}
    	        	
    
    
    	        	
    	        }
    	        else if(state==='answer')
    	        {
                   
    	        	var opponent=getOpponentByContext(event.contextobj);
    	        	user = getUserById(event.sender);
    	        	var word = 	context.simpledb.botleveldata["word_"+opponent.userid];
    	        	var position_user = userArray.indexOf(user);
    	        	var position_oppoent = userArray.indexOf(opponent);
                    
                    if(word && user.playingwith!=='')
                    {
                        
                   
    	        	if(word.toLowerCase()===msg.toLowerCase())
    	        	{
    	        		var opponent_hint = context.simpledb.botleveldata["hint_"+event.sender];
    	        		SendMessage(opponent.botname,opponent.contextobj,apikey,user.name+' answered correctly..!('+(opponent_hint-1)+' hints used)\nWait to receive a challenge from '+user.name);
    	        		
    
    
    	        		opponent.waiting=true;
    
    	        		user.waiting=false;
    	        		userArray[position_user]=user;
    	        	    userArray[position_oppoent]=opponent;
    	        		SendMessage(user.botname,user.contextobj,apikey,user.name+' Bravo....\nCorrect answer..!\n'+'Now, it\'s your turn to provide a challenge for '+opponent.name);
    	        		context.simpledb.botleveldata["state_"+event.sender]='question';
    					context.simpledb.botleveldata["state_"+opponent.userid]='answer';
    					context.simpledb.botleveldata["word_"+opponent.userid]='';
    	        		
    	        		context.simpledb.botleveldata["word_"+event.userid]='';
    	        		context.simpledb.botleveldata["hint_"+event.sender]=1;
    	        		 context.simpledb.saveData();
    	        		
    	        	}
    	        	else
    	        	{
    	        	    if(context.simpledb.botleveldata["word_"+opponent.userid]!='')
    	        		{
    	        		//SendMessage(opponent.botname,opponent.contextobj,apikey,'Wrong Answer by '+user.name+'....!!!\n Say \'stop \' to stop playing');
    	        	    SendMessage(user.botname,user.contextobj,apikey,'Wrong answer ..!\n Say \'pass\' if you can\'t solve it\nSay \'hint\' to get help (max 3 times)');
    	        	
    	        		}
    	        		else
    	        		{
    	        		    SendMessage(user.botname,user.contextobj,apikey,'Please wait till '+opponent.name+' sends you a challenge.');
    	        		}
    	        	  }
                    }
                    else
                    {
                        SendMessage(user.botname,user.contextobj,apikey,'Please wait till '+opponent.name+' sends you a challenge.');
                    }
    
    	        	 context.simpledb.botleveldata.userArray=userArray;	
    	        	
    
    	          }
    	        else
    	        {
    	            var link=getLink(channel);
    	            
    	            
    	        	if(check_user(event.contextobj))
    	        		 {
                            
                            user =  getUserById(event.sender);
    	                    context.sendResponse('Hi, this is the jumblewithfriends bot. You and a friend take turns sending jumbled words to each other. Un-jumble the word, if you can, to see the coded message. It\'s a great game to play with friends.\n\nTo start, either you enter your friend\'s passcode or ask the friend to enter yours.  \n\nSay \'Play <passcode>\'to start game.\n\nSay \'Stop\' to exit game at any time\n\nTo invite your friend to play, just forward this message to them: '+'\n\nLink -->'+link+'\n\n'+user.name+'\'s passcode-->'+user.password);	
    	        		 }
    	        		 else
    	        		 {
    	        		 	addUser(event);
    	        		 	user =  getUserById(event.sender);
    	                    context.sendResponse('Hi, this is the jumblewithfriends bot. You and a friend take turns sending jumbled words to each other. Un-jumble the word, if you can, to see the coded message. It\'s a great game to play with friends.\n\nTo start, either you enter your friend\'s passcode or ask the friend to enter yours.  \n\nSay \'Play <passcode>\'to start game.\n\nSay \'Stop\' to exit game at any time\n\nTo invite your friend to play, just forward this message to them: '+'\n\nLink -->'+link+'\n\n'+user.name+'\'s passcode-->'+user.password);	
    	                    
    	        		 }
    
    
    	            
    	        }
    	        
    	        
    	        
    	        
    	    }
    	    function scramble(originalword)
      				 {
    			    	var splitword = originalword.split("");
    			    	for(var i=0 ; i<splitword.length; i++)
        				{
        				var r = Math.floor((Math.random() * splitword.length));
        				var temp = splitword[i];
        				splitword[i] = splitword[r];
        				splitword[r] = temp;
        				}
        				var scrambleword = splitword.join("");
        	
        				if(originalword == scrambleword && originalword.length >1)
    	    			{
    		    		scrambleword = scramble(originalword);
    	    			}
        				return scrambleword;
        			}
    
    	    function addUser(event)
    	    {
     				var userArray=context.simpledb.botleveldata.userArray;
    	            var password = Math.random()*10000;
                     
                     
            if(!userArray)
            {
               userArray=[];
            }
                var user={
                name:'',
                userid:'',
                password:0,
                word:'',
                playingwith:'',
                contextobj:'',
                status:'offline',
                botname:'',
                waiting:false
            }; 
                    
                    
                    
                     
    	            
    	         while(parseInt(password)<=999)
    	            {
    	            password = Math.random()*10000;
                
    	                
    	            }
    	            
    	                password=parseInt(password);    
    	              
                    
                
    	            var boolean = check_password(userArray,password);
    	            while(boolean)
    	            {
    	               boolean = check_password(userArray,password);
    
    	   			}
    	   			
    	   			
    
    	   			user.userid=''+event.sender;
    	   			user.password=password;
    	   			user.name=event.senderobj.display;
    	   			user.playingwith='';
    	   			user.contextobj=event.contextobj;
    	   			user.status='offline';
    	   			user.botname=event.botname;
    
    	   			userArray.push(user);
    	   			
    	   			
                    context.simpledb.botleveldata.userArray=userArray;
                   
    	    }
    	    
    	    function check_password(userArray, password) {
    	     	var boolean = false;
    
    
    	     	if(userArray)
    	     	{
    	     		
    	   
                    for(var i = 0 ; i<(userArray.length);i++)
                    {
        				if(userArray[i].password===password)
        			    {
        				  boolean=true;
        				  break;	
        			    }
                      
                    }
    
    
    
    	     	}	
    
    
    
    
    
    
    	     	return boolean
    	    }
    
    	    function getUserPositionByPasscode(passcode)
    	    {	 var userArray=context.simpledb.botleveldata.userArray;
    			var position;
    
                if(userArray)
                {
    	    	for(var i = 0 ; i<(userArray.length);i++)
                    {
                    	if(userArray[i].password===passcode)
        			    {
        			    	
        			    	position=i; 
        			    	break;
        			    }	
    
                    }
                }
    
    	    	return position;	    	
    	    }
    
    	    function getUserPositionByUserID(userid)
    	    {
    			var position;
     			var userArray=context.simpledb.botleveldata.userArray;
    	    	for(var i = 0 ; i<(userArray.length);i++)
                    {
                    	if(userArray[i].userid===userid)
        			    {
        			    	
        			    	position=i; 
        			    	break;
        			    }	
    
                    }
    
    
    	    	return position;	    	
    	    }
    	    function getOpponentByContext(contextobj)
    	    {
    	        
    	        
    	    	 var userArray=context.simpledb.botleveldata.userArray;
    	    	var user;
    	    	if(userArray)
    	    	{
    	    	for(var i = 0 ; i<(userArray.length);i++)
                    {
                        
                    	if(JSON.stringify(userArray[i].playingwith)==JSON.stringify(contextobj))
        			    {
        			    	
        			    	user=userArray[i]; 
        			    	break;
        			    }
        			   
    
                    }
                    
    	    	}
                    
                    
                // context.sendResponse(userArray);    
    	    	return user;
    	    }
    	    function getOpponent(passcode)
    	    {
                passcode = parseInt(passcode);
    	    	 var userArray=context.simpledb.botleveldata.userArray;
    	    	var user;
    
                if(userArray)
    	    	{
    	    	for(var i = 0 ; i<(userArray.length);i++)
                    {
                    	if(userArray[i].password===passcode)
        			    {
        			    	
        			    	user=userArray[i]; 
        			    	break;
        			    }	
    
                    }
    
                }
    	    	return user;
    
    	    }
    	
    	    function get_user_name(userArray,contextobj)
    	    {
    	    	var name='';
              
    	    	for(var i = 0 ; i<(userArray.length);i++)
                    {
                    	if(JSON.stringify(userArray[i].playingwith)==JSON.stringify(contextobj))
        			    {
        			     name = userArray[i].name;
        			     break;
        			    }	
                    }
    
    
    	    	return name;
    	    }
    	    function getUserById(userid)
    	    {
    	    	var userArray=context.simpledb.botleveldata.userArray;
    	    	var user;
    	    	if(userArray)
    	    	{
    
    	    	for(var i = 0 ; i<(userArray.length);i++)
                    {
                    	if(userArray[i].userid===userid)
        			    {
        			    	
        			    	user=userArray[i]; 
        			    	break;
        			    }	
    
                    }
    
    	    	}
    	    	return user;	
    
    	    }
    	   function check_user(contextobj)
    	    {
    	    	var boolean =false;
    	    	var userArray=context.simpledb.botleveldata.userArray;
    	    	if(userArray)
    	    	{
    	    	
    	    	for(var i = 0 ; i<(userArray.length);i++)
                    {
                        context.console.log('userArray : '+JSON.stringify(userArray[i].contextobj)+'\nequality : '+JSON.stringify(contextobj));
                    	if(JSON.stringify(userArray[i].contextobj)==JSON.stringify(contextobj))
        			    {
        			     boolean=true;
        			     break;
        			    }	
                    }
    	    	}
    	    	return boolean;
    	    }
    	    /** Functions declared below are required **/
    
    
    	    function getLink(channel)
    	    {
    	    	var link;
    	    	switch(channel)
                    	{
                    		case 'slack' :
                    		link='https://gupshup-hackers.slack.com/messages/@gsdemo2';
                    		break;
                    		case 'telegram' :
    						link='https://web.telegram.org/#/im?p=@Gsdemo1bot';
                    		break;
                    		case 'fb':
                    		link='https://www.facebook.com/messages/943144075801359';
                    		break;
                    		default:
                    		link='go in your '+channel+' app and find gupshup proxy bot and type \'demo \'';
                    		break;
                    	}
    	    	return link;
    	    }
    	    function EventHandler(context, event) {
    	        
    	        var userArray=context.simpledb.botleveldata.userArray;
    	        
    	    	 var user;
    	    	 var channel = event.contextobj.channeltype;
    	    	   var link=getLink(channel);
    	              if(check_user(event.contextobj))
    	        		 {
                            
                    var opponent=getOpponentByContext(event.contextobj);
    	        	 
                   	    
    	        	user = getUserById(event.sender);
    	            
    	            var position_user = userArray.indexOf(user);
    	        	var position_oppoent = userArray.indexOf(opponent);
    	            
    	            
    	            
            	            if(opponent && user)
            	            {
            	            user.status='offline';
        	                user.waiting=false;
        	                user.playingwith='';
        	                opponent.status='offline';
        	                opponent.waiting=false;
        	                opponent.playingwith='';
                         	userArray[position_user]=user;
                            userArray[position_oppoent]=opponent;
                            context.simpledb.botleveldata.userArray=userArray;
                            context.simpledb.botleveldata["state_"+event.sender]='';
            				context.simpledb.botleveldata["state_"+opponent.userid]='';
        	                 } 
        	                 else
        	                 {
        	                 user.status='offline';
        	                user.waiting=false;
        	                user.playingwith='';
        	                    userArray[position_user]=user;
        	                   context.simpledb.botleveldata["state_"+event.sender]='';  
        	                 }
        	                 context.sendResponse('Hi, this is the jumblewithfriends bot. You and a friend take turns sending jumbled words to each other. Un-jumble the word, if you can, to see the coded message. It\'s a great game to play with friends.\n\nTo start, either you enter your friend\'s passcode or ask the friend to enter yours.  \n\nSay \'Play <passcode>\'to start game.\n\nSay \'Stop\' to exit game at any time\n\nTo invite your friend to play, just forward this message to them: '+'\n\nLink -->'+link+'\n\n'+user.name+'\'s passcode-->'+user.password);	
    	        		 }
    	        		 else
    	        		 {
    	        		 	addUser(event);
    	        		 	user =  getUserById(event.sender);
    	                    context.sendResponse('Hi, this is the jumblewithfriends bot. You and a friend take turns sending jumbled words to each other. Un-jumble the word, if you can, to see the coded message. It\'s a great game to play with friends.\n\nTo start, either you enter your friend\'s passcode or ask the friend to enter yours.  \n\nSay \'Play <passcode>\'to start game.\n\nSay \'Stop\' to exit game at any time\n\nTo invite your friend to play, just forward this message to them: '+'\n\nLink -->'+link+'\n\n'+user.name+'\'s passcode-->'+user.password);	
    	        		 }
    	    }
    
    
    	    function HttpResponseHandler(context, event) {
    	        // if(event.geturl === "http://ip-api.com/json")
    	        context.sendResponse(event.getresp);
    	    }
    	
    	    function DbGetHandler(context, event) {
    	        context.sendResponse("testdbput keyword was last get by:" + event.dbval);
    	    }
    	
    	    function DbPutHandler(context, event) {
    	        context.sendResponse("testdbput keyword was last put by:" + event.dbval);
    	    }
    
    function getSentenceHint(word,hint_count)
    {
    
    
    var wordArray = word.split(" ");
    
    var sentenceHint='';
    
    if(wordArray.length>=2)
    {
    
    for(var i=0;i<wordArray.length;i++)
    {
       var s = wordArray[i];
       var scramble1='';
       var chunklength=s.length;
      if(chunklength>2)
    {
       var chunk = s;
       scramble1 = HintWord(chunk,hint_count);
       
    }
    else
    {
    scramble1 =getHintWord(s.length);
   // alert(s);
    }
    sentenceHint+=' '+scramble1 
    }
    
    }
    else
    {
     sentenceHint=HintWord(word,hint_count);
    
    }
    
    
    
    return sentenceHint;
    
    
    }
    
    
    
    
    
    
    
    function HintWord(word,hint_count,event)
    {
    
    
                 var wordlength = parseInt(word.length);
              var scramble1='';
                         if(wordlength>6)
                {
    
                 switch(parseInt(hint_count))
                 {
                  case 1:
                  var mid = parseInt(wordlength/2);
                   scramble1 = getHintWord(wordlength);
                                     
                 // context.sendResponse(scramble1.charAt(mid));   
                  scramble1=replaceAt(mid,word.charAt(mid),scramble1);
             break;
    
                  case 2:
                  var mid = parseInt(wordlength/2);
                   scramble1 = getHintWord(wordlength);
                  
                  //context.sendResponse(scramble1.charAt(mid));
                  scramble1=replaceAt(mid,word.charAt(mid),scramble1);
                  scramble1=replaceAt(mid+2,word.charAt(mid+2),scramble1);
                  break;
    
                  case 3:
                  var mid = parseInt(wordlength/2);                   scramble1 = getHintWord(wordlength);
                  scramble1=replaceAt(mid,word.charAt(mid),scramble1);
                  scramble1=replaceAt(mid+2,word.charAt(mid+2),scramble1);
                  scramble1=replaceAt(mid-2,word.charAt(mid-2),scramble1);
                  break;
                 }
    
                }
                else
                {
                 switch(parseInt(hint_count))
                 {
                  case 1:
                  var mid = parseInt(wordlength/2);
                      scramble1 = getHintWord(wordlength);
                  
                 // context.sendResponse(scramble1.charAt(mid));
                  scramble1=replaceAt(mid,word.charAt(mid),scramble1);
                   
                  break;
    
                  case 2:
                  var mid = parseInt(wordlength/2);
                     scramble1 = getHintWord(wordlength);
                  
                 // context.sendResponse(scramble1.charAt(mid));
                  scramble1=replaceAt(mid,word.charAt(mid),scramble1);
                  scramble1=replaceAt(mid+1,word.charAt(mid+1),scramble1);
                  
                  break;
    
                  case 3:
                  var mid = parseInt(wordlength/2);
                  scramble1 = getHintWord(wordlength);
                  
                 // context.sendResponse(scramble1.charAt(mid));
                  scramble1=replaceAt(mid,word.charAt(mid),scramble1);
                  scramble1=replaceAt(mid+1,word.charAt(mid+1),scramble1);
                  scramble1=replaceAt(mid-1,word.charAt(mid-1),scramble1);
                 
                  break;
                 }
                }
    
    return scramble1;
    }
    
    	    function getHintWord(wordlength)
    	    {
    	    	var word='';
    	    	
    	    	for (var i=0;i<wordlength;i++) 
    	    	{
    	    		word+='*';
    	    		
    	    	}
    	    	return word
    	    }
    
    	    
    	    
    	      function replaceAt(index, character,word) {
    
            return word.substr(0, index) + character + word.substr(index+character.length);
               }
    	   function SendMessage(botname,contextobj,apikey,message)
    	    {
                botname = encodeURI(botname);
                contextobj = encodeURI(JSON.stringify(contextobj));
                apikey = encodeURI(apikey);
                message = encodeURI(message);
                var url = "https://api.gupshup.io/sm/api/bot/"+botname+"/msg";
                var body = "botname="+botname+"&context="+contextobj+"&message="+message;
                var headers = "{\"Accept\": \"application/json\",\"apikey\": \""+apikey+"\",\"Content-Type\": \"application/x-www-form-urlencoded\"}";
                context.simplehttp.makePost(url,body,JSON.parse(headers));
    	    }
