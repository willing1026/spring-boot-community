var app = {
    init: function() {
        let _this = this;
        
        $('#btn-register').on('click', function() {
            _this.register();
        });
    },
    
    register: function() {
        
        alert('register start');
        
        let registerData= {
            idx: $('#user-idx').val(),
            email: $('#user-email').val(),
            name: $('#user-name').val(),
            password: $('#user-password').val()
        };
        
        $.ajax({
            url: '/register',
            type: 'PUT',
            dataType: 'text',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(registerData),
            success: function() {
                alert('등록완료');
            },
            error: function() {
                alert('등록오류');
            }
        }).always(function() {
            location.reload();
        });
        
    }
}

app.init();