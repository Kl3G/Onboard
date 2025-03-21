
    function toggleCommentForm(button) {
        const formComment = button.parentElement.parentElement.nextElementSibling; // 버튼 바로 다음에 위치한 폼

        /* ** 참고 **
        1. document.getElementById("formComment");
        이 방식은 id가 "formComment"인 첫 번째 요소만 찾습니다.
        HTML 문서에는 ** 동일한 id 값을 가진 요소가 여러 개 있을 수 없다고 가정 **하므로,
        이 방식은 페이지에 여러 댓글 폼이 있을 때 모두 관리하기 어렵습니다.
        다수의 댓글 폼이 필요하다면 이 접근 방식이 적합하지 않습니다.

        2. button.nextElementSibling.nextElementSibling;
        이 방식은 버튼 위치를 기준으로 다음의 두 번째 형제 요소를 찾아 접근합니다.
        따라서, 여러 댓글 폼을 각 댓글 요소에 넣어야 할 경우 더 적합합니다.
        HTML에서 li 내의 댓글에 따라 폼이 개별적으로 위치하고 있으므로,
        이 방식을 사용하면 버튼을 클릭할 때마다 각 댓글에 해당하는 폼을 정확히 접근할 수 있습니다.

        3. const formComment = button.nextElementSibling.nextElementSibling.nextElementSibling;
        실행되지 않는 이유는 nextElementSibling을 사용했기 때문입니다.
        nextElementSibling은 현재 요소의 바로 다음 형제 요소를 가리킵니다.
        이 경우, <div> 안에 있는 button 요소 다음에는 <div> (댓글 입력 폼) 요소가 존재하고,
        그 다음에 더 이상 형제 요소가 없기 때문에 세 번째 nextElementSibling은 null이 됩니다.
        */

        if (formComment) {
            // 현재 display 스타일을 확인하여 폼을 보이게 하거나 숨김
            if (formComment.style.display === "none" || formComment.style.display === "") {
                formComment.style.display = "block"; // 폼을 보이게 설정
            } else {
                formComment.style.display = "none"; // 폼을 숨기기
            }
        }
    }

    // 대댓글이 존재할 때만 padding-bottom: 15px; 적용
    document.addEventListener("DOMContentLoaded", function() {
      const commentElements = document.querySelectorAll('#comment');

      commentElements.forEach(commentElement => {
        const childComment = commentElement.querySelector('#childcomment');

        if (childComment) {
          commentElement.style.paddingBottom = '15px';
        } else {
          commentElement.style.paddingBottom = '0';
        }
      });
    });


    // X 버튼 누르면 대댓글 삭제 form 출력
    function togglecommentDel(button) {

        const formComment = button.parentElement.previousElementSibling;
        /*const divElement = button.parentNode.parentNode;
        const dateBox = divElement.getElementsByTagName("div")[2];*/

        if (formComment) {
            // 현재 display 스타일을 확인하여 폼을 보이게 하거나 숨김
            if (formComment.style.display === "none" || formComment.style.display === "") {

                formComment.style.display = "flex"; // 폼을 보이게 설정
                let input = formComment.querySelector('[id^="cpwd-"]');
                input.focus();
            } else {

                formComment.style.display = "none"; // 폼을 숨기기
            }
        }
    }
    // --------------------------------------------------------------------


    // X 버튼 누르면 대댓글 삭제 form 출력
    function toggleChildCommentDel(button) {

        const formComment = button.parentElement.nextElementSibling;
        /*const liElement = button.parentNode.parentNode;
        const dateBox = liElement.getElementsByTagName("div")[2];*/

        if (formComment) {
            // 현재 display 스타일을 확인하여 폼을 보이게 하거나 숨김
            if (formComment.style.display === "none" || formComment.style.display === "") {

                formComment.style.display = "flex"; // 폼을 보이게 설정
                let input = formComment.querySelector('[id^="ccpwd-"]');
                input.focus();
            } else {

                formComment.style.display = "none"; // 폼을 숨기기
            }
        }
    }
    // --------------------------------------------------------------------


    // 댓글, 대댓글 index 선택 함수
    document.addEventListener('DOMContentLoaded', () => {

        // 댓글 삭제 폼 submit 버튼에 마우스 커서 올렸을 때 해당 댓글의 idx와 pwd.value 불러온다.
        document.querySelectorAll('.submitBtn').forEach(btn => {

            btn.addEventListener('mouseover', (e) => {

                const parent = e.target.closest('.commentDiv');
                if (parent) {

                    const inputElem = parent.querySelector('[id^="cidx-"]');
                    if (inputElem) {

                        window.commentId = inputElem.id.replace('cidx-', '');
                        console.log(window.commentId);
                    }

                    const parent2 = e.target.closest('.commentDiv');
                    const inputElem2 = parent2.querySelector('[id^="cpwd-"]');
                    if (inputElem) {

                        window.commentPwd = inputElem2.value;
                        console.log(window.commentPwd);
                    }
                }
            })
        })

        // 댓글 삭제 폼 input에 focus할 때 해당 댓글의 idx와 pwd.value 불러온다.
        document.querySelectorAll('.cpwdInput').forEach(input => {

            input.addEventListener('focus', (e) => {

                const parent = e.target.closest('.commentDiv');
                const inputElem = parent.querySelector('[id^="cidx-"]');
                window.commentId = inputElem.id.replace('cidx-', '');
                console.log(window.commentId);

                const parent2 = e.target.closest('.commentDiv');
                const inputElem2 = parent2.querySelector('[id^="cpwd-"]');
                window.commentPwd = inputElem2.value;
                console.log(window.commentPwd);
            })
        })

        // 댓글 삭제 폼 input에 입력값이 변할 때 해당 댓글의 idx와 pwd.value 불러온다.
        document.querySelectorAll('.cpwdInput').forEach(input => {

            input.addEventListener('input', (e) => {

                const parent = e.target.closest('.commentDiv');
                const inputElem = parent.querySelector('[id^="cidx-"]');
                window.commentId = inputElem.id.replace('cidx-', '');
                console.log(window.commentId);

                const parent2 = e.target.closest('.commentDiv');
                const inputElem2 = parent2.querySelector('[id^="cpwd-"]');
                window.commentPwd = inputElem2.value;
                console.log(window.commentPwd);
            })
        })
        // --------------------------------------------------------------------


        // 대댓글 삭제 폼 submit 버튼에 마우스 커서 올렸을 때 해당 댓글의 idx와 pwd.value 불러온다.
        document.querySelectorAll('.submitBtn').forEach(input => {

            input.addEventListener('mouseover', (e) => {

                const parent = e.target.closest('.commentDiv2'); // 가장 가까운 .commentDiv2 탐색.
                if (parent) {

                    const inputElem = parent.querySelector('[id^="ccidx-"]'); // ccidx- 로 시작하는 ID를 가진 요소 탐색.
                    if (inputElem) {

                        window.childCommentId = inputElem.id.replace('ccidx-', ''); // ID에서 ccidx- 를 삭제하고 index만 남긴다.
                        console.log(window.childCommentId);
                    }

                    const parent2 = e.target.closest('.commentDiv2');
                    const inputElem2 = parent2.querySelector('[id^="ccpwd-"]');
                    if (inputElem2) {

                        window.childCommentPwd = inputElem2.value;
                        console.log(window.childCommentPwd);
                    }
                }
            });
        });

        // 대댓글 삭제 폼 input에 focus할 때 해당 댓글의 idx와 pwd.value 불러온다.
        document.querySelectorAll('.ccpwdInput').forEach(input => {

            input.addEventListener('focus', (e) => {

                const parent = e.target.closest('.commentDiv2');
                const inputElem = parent.querySelector('[id^="ccidx-"]');
                window.childCommentId = inputElem.id.replace('ccidx-', '');
                console.log(window.childCommentId);

                const parent2 = e.target.closest('.commentDiv2');
                const inputElem2 = parent2.querySelector('[id^="ccpwd-"]');
                window.childCommentPwd = inputElem2.value;
                console.log(window.childCommentPwd);
            })
        })

        // 대댓글 삭제 폼 input에 입력값이 변할 때 해당 댓글의 idx와 pwd.value 불러온다.
        document.querySelectorAll('.ccpwdInput').forEach(input => {

            input.addEventListener('input', (e) => {

                const parent = e.target.closest('.commentDiv2');
                const inputElem = parent.querySelector('[id^="ccidx-"]');
                window.childCommentId = inputElem.id.replace('ccidx-', '');
                console.log(window.childCommentId);

                const parent2 = e.target.closest('.commentDiv2');
                const inputElem2 = parent2.querySelector('[id^="ccpwd-"]');
                window.childCommentPwd = inputElem2.value;
                console.log(window.childCommentPwd);
            })
        })
        // --------------------------------------------------------------------
    });
    // --------------------------------------------------------------------


    // 댓글 삭제 함수
    function checkCommentPwd(event) {

        event.preventDefault(); // 기본 폼 제출 방지

        let form = event.target;

        let bidx = document.getElementById('bidx0').value;
        let pidx = document.getElementById('pidx0').value;

        let cpwd = window.commentPwd || 0;
        let cidx = window.commentId || 0;

        $.ajax({

            type: "post",
            url: "/checkCommentPwd", // 비밀번호 확인 요청을 처리할 URL
            data: {cidx: cidx, cpwd: cpwd},
            success: function(response) {

                if(response.success){

                    let form = document.createElement('form');
                    form.method = 'POST';
                    form.action = '/commentDel';

                    let bidxField = document.createElement('input');
                    bidxField.type = 'hidden';
                    bidxField.name = 'bidx';
                    bidxField.value = bidx;

                    let pidxField = document.createElement('input');
                    pidxField.type = 'hidden';
                    pidxField.name = 'pidx';
                    pidxField.value = pidx;

                    let cidxField = document.createElement('input');
                    cidxField.type = 'hidden';
                    cidxField.name = 'cidx';
                    cidxField.value = cidx;

                    form.appendChild(cidxField); // 폼에 pidx 필드 추가
                    form.appendChild(bidxField);
                    form.appendChild(pidxField);

                    // 폼을 body에 추가하고 전송
                    document.body.appendChild(form);
                    form.submit(); // 폼을 전송하여 POST 요청 실행
                }else if(response.success2) {

                    alert("パスワードが一致しません。");
                }

            },
            error: function() {

                alert("サーバーエラーが発生しました。");
            }
        });
    }
    // --------------------------------------------------------------------


    // 대댓글 삭제 함수
    function checkChildCommentPwd(event) {

        event.preventDefault(); // 기본 폼 제출 방지

        let form = event.target;

        let bidx = document.getElementById('bidx20').value;
        let pidx = document.getElementById('pidx20').value;

        let ccpwd = window.childCommentPwd || 0;
        let ccidx = window.childCommentId || 0;

        $.ajax({

            type: "post",
            url: "/checkChildCommentPwd", // 비밀번호 확인 요청을 처리할 URL
            data: {ccidx: ccidx, ccpwd: ccpwd},
            success: function(response) {

                if(response.success){

                    let form = document.createElement('form');
                    form.method = 'POST';
                    form.action = '/childCommentDel';

                    let bidxField = document.createElement('input');
                    bidxField.type = 'hidden';
                    bidxField.name = 'bidx';
                    bidxField.value = bidx;

                    let pidxField = document.createElement('input');
                    pidxField.type = 'hidden';
                    pidxField.name = 'pidx';
                    pidxField.value = pidx;

                    let ccidxField = document.createElement('input');
                    ccidxField.type = 'hidden';
                    ccidxField.name = 'ccidx';
                    ccidxField.value = ccidx;

                    form.appendChild(ccidxField); // 폼에 pidx 필드 추가
                    form.appendChild(bidxField);
                    form.appendChild(pidxField);

                    // 폼을 body에 추가하고 전송
                    document.body.appendChild(form);
                    form.submit(); // 폼을 전송하여 POST 요청 실행
                }else if(response.success2) {

                    alert("パスワードが一致しません。");
                }

            },
            error: function() {

                alert("サーバーエラーが発生しました。");
            }
        });
    }
    // --------------------------------------------------------------------


    // 게시글 삭제 함수
    function checkPassword(event, action) {
      event.preventDefault(); // 기본 폼 제출 방지

      let ppwd = document.getElementById('ppwd').value;
      let pidx = document.getElementById('pidx').value;
      let bidx = document.getElementById('bidx').value;
      let userid = document.getElementById('userid').value;


      $.ajax({
        type: "POST",
        url: "/checkPostPassword", // 비밀번호 확인 요청을 처리할 URL
        data: { ppwd: ppwd, pidx: pidx, bidx: bidx, userid: userid},
        contentType: "application/x-www-form-urlencoded",
        success: function(response) {
          if(response.success){

              if(action === 'modify'){

                  location.href = `/modifyPost?pidx=${pidx}&bidx=${bidx}&ppwd=${ppwd}`;
              }else if(action === 'delete'){

                  let form = document.createElement('form');
                  form.method = 'POST';
                  form.action = '/postDel';

                  let pidxField = document.createElement('input');
                  pidxField.type = 'hidden';
                  pidxField.name = 'pidx';
                  pidxField.value = pidx;

                  let bidxField = document.createElement('input');
                  bidxField.type = 'hidden';
                  bidxField.name = 'bidx';
                  bidxField.value = bidx;

                  form.appendChild(pidxField); // 폼에 pidx 필드 추가
                  form.appendChild(bidxField);

                  // 폼을 body에 추가하고 전송
                  document.body.appendChild(form);
                  form.submit(); // 폼을 전송하여 POST 요청 실행
              }
          } else if(response.success1) {

              alert("権限がないアカウントです。");
          } else if(response.success2) {

              alert("パスワードが一致しません。");
          }
        },
        error: function() {
          alert("サーバーエラーが発生しました。");
        }
      });
    }
    // --------------------------------------------------------------------


    // 게시글 좋아요 증가, 출력
    /*1. 첫 번째 콜백 함수가 정상적인 값을 return 하면 then() 이 반환하는 new Promise 는 콜백 함수가 반환한 값으로 즉시 resolve 된다.
      2. 첫 번째 콜백 함수가 새로운 Promise 를 return 하면 then() 이 반환하는 new Promise 는 콜백 함수가 반환하는 Promise 를 따라서 나중에 resolve 또는 reject 된다.
      3. then()의 2개의 인자(콜백 함수) 중 순서에 상관 없이 어느 콜백 함수라도 throw 가 실행되면 반드시 reject 가 되고, 정상정인 값이 리턴되면 resolve 가 된다.
      4. then() 인자(콜백 함수)에서 실행되는 throw 는 데이터를 전달하는 게 아니라 반환되는 데이터가 reject 로 반환되게 하는 역할이다.*/
    document.querySelectorAll(".like-btn").forEach(button => {

        button.addEventListener("click", function () {

            const pidx = this.dataset.no; // data-no 값 가져오기

            fetch(`/like?pidx=${pidx}`, { method: "POST" }) // fetch() 는 response 객체를 반환한다.
                .then(response => {

                    if (!response.ok) { // 단순히 404나 500 같은 HTTP 에러 코드는 reject 하지 않고,
                    // response.ok가 false 인 상태로 resolve 하기 때문에 response.ok를 확인해야 한다.

                        return response.text().then(text => {

                            alert(text);
                            throw new Error(`HTTP error ${text}`);
                        });
                    }

                    const countSpan = this.nextElementSibling.nextElementSibling; // 좋아요 수 표시 요소
                    countSpan.textContent = parseInt(countSpan.textContent) + 1;
                    return response.text();
                })
                .catch(error => console.error(error));
        });
    });
    // --------------------------------------------------------------------