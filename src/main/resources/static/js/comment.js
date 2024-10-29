/*
function submitComment() {
  // 폼 데이터를 가져옵니다.
  const formData = new FormData(document.getElementById("commentForm"));

  // AJAX 요청 설정
  fetch("/comment_proc", {
    method: "POST",
    body: formData,
  })
    .then(response => {
      if (response.ok) {
        return response.text();  // 서버에서 텍스트 응답을 기대하는 경우
      } else {
        throw new Error("댓글 등록에 실패했습니다.");
      }
    })
    .then(data => {
      alert("댓글이 등록되었습니다!");
      // 필요한 경우 댓글 등록 후 처리 로직 추가
    })
    .catch(error => {
      console.error(error);
      alert("댓글 등록 중 오류가 발생했습니다.");
    });
}*/

/*function formComment(){

    alert("댓글 작성 중입니다.");

    const formComment = document.getElementById("formComment");
    if (formComment) {
        formComment.style.display = "block"; // 요소를 보이게 설정
    }
}*/

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

function toggleChildCommentDel(button) {
    const formComment = button.parentElement.nextElementSibling;

    if (formComment) {
        // 현재 display 스타일을 확인하여 폼을 보이게 하거나 숨김
        if (formComment.style.display === "none" || formComment.style.display === "") {
            formComment.style.display = "block"; // 폼을 보이게 설정
        } else {
            formComment.style.display = "none"; // 폼을 숨기기
        }
    }
}

function togglecommentDel(button) {
    const formComment = button.parentElement.nextElementSibling;

    if (formComment) {
        // 현재 display 스타일을 확인하여 폼을 보이게 하거나 숨김
        if (formComment.style.display === "none" || formComment.style.display === "") {
            formComment.style.display = "block"; // 폼을 보이게 설정
        } else {
            formComment.style.display = "none"; // 폼을 숨기기
        }
    }
}