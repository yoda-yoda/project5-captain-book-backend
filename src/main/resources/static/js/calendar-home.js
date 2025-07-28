// Calendar 홈 html의 모든 수정 버튼 리스트 추출
let editBtnList = document.querySelectorAll(".edit-btn");

// Calendar 홈 html의 모든 삭제 버튼 리스트 추출
let deleteBtnList = document.querySelectorAll(".delete-btn");


// Calendar 수정 모달 객체의 form 추출
const editModalForm = document.querySelector("#editModal .modal-footer form");

// Calendar 삭제 모달 객체의 form 추출
const deleteModalForm = document.querySelector("#deleteModal .modal-footer form");





// 모든 수정 버튼에 이벤트 리스너 등록
// 수정 버튼 클릭 시 해당 calendarId를 가져와 모달 form의 action에 설정
editBtnList.forEach(
    function(editBtn) {
        editBtn.addEventListener("click", function() {
            let calendarId = editBtn.dataset.calendarId;
            editModalForm.setAttribute("action",`/calendar/update/${calendarId}`)
        });
    });


// 모든 삭제 버튼에 이벤트 리스너 등록
// 삭제 버튼 클릭 시 해당 calendarId를 가져와 모달 form의 action에 설정
deleteBtnList.forEach(
    function(deleteBtn) {
        deleteBtn.addEventListener("click", function() {
            let calendarId = deleteBtn.dataset.calendarId;
            deleteModalForm.setAttribute("action",`/calendar/delete/${calendarId}`)
        });
    });




// 뒤로가기 버튼 클릭 시 부트스트랩 수정 모달 수동으로 닫는 기능
// 제공 API로 닫으려니 애니메이션 프레임 잔상이 남아 껄끄러워서 추가한 기능
const editModalConfirmBtn = document.querySelector("#editModal .confirm-btn");

editModalConfirmBtn.addEventListener("click", function() {
   
    const editModal = document.querySelector("#editModal");
   
   editModal.style.display = 'none';
//    editModal.setAttribute("aria-hidden", "true");
//    editModal.removeAttribute("aria-modal");
   editModal.removeAttribute("role");
   document.body.classList.remove("modal-open");
   document.body.style = "";
   document.querySelectorAll(".modal-backdrop").
   forEach(function(backdrop) {
       backdrop.remove()});
   editModal.classList.remove("show");
   const instance = bootstrap.Modal.getInstance(editModal) || new bootstrap.Modal(editModal);
   instance.dispose();

});


// 부트스트랩 수정 모달창이 닫힐때 스크린 리더 접근성 오류를 방지하기 위한 함수
document.body.addEventListener("hide.bs.modal", function(event) {
    const focusBtn = document.querySelector('.calendar-create-btn');

    focusBtn.focus();

    if (focusBtn) {
        // 렌더링 후 포커스 이동 안정화를 위해 약간 지연
        setTimeout(() => focusBtn.focus(), 0);
    }

});


