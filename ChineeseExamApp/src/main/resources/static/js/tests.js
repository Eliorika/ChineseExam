var isSubmittable = false;

function skipQuestion(isSkip) {
    console.log(isSkip)
    var form = document.getElementById('question_form');
    // Добавляем параметр запроса skip=true к URL формы
    form.action = form.action + "?skip=" + isSkip;
    // Отправляем форму
    //form.submit();
}

// window.addEventListener('beforeunload', function (event) {
//
//
//     console.log(isSubmittable);
//     if (!isSubmittable) {
//         var xhr = new XMLHttpRequest();
//         console.log(xhr);
//         xhr.open('POST', './clear', true); // true для асинхронного запроса
//
//         xhr.send();
//
//         //var confirmationMessage = 'Ваш прогресс будет потерян. Хотите уйти?';
//
//         // Показываем пользовательское окно с предупреждением и получаем ответ пользователя
//         //window.confirm(confirmationMessage);
//
//         // var xhr = new XMLHttpRequest();
//         // xhr.open('POST', '/test/clear', true); // true для асинхронного запроса
//         // xhr.send();
//         // console.log(userResponse);
//         // // // Если пользователь подтверждает закрытие окна
//         // // if (userResponse) {
//         // //     // Выполняем запрос на бекенд
//         // //     var xhr = new XMLHttpRequest();
//         // //     xhr.open('POST', '/test/clear', true); // true для асинхронного запроса
//         // //     xhr.send();
//         // // } else {
//         // //     // Если пользователь отменяет закрытие окна, отменяем действие по умолчанию (закрытие окна)
//         // //     event.preventDefault();
//         // // }
//     }
// });


function selectVariant(element) {
    var variant = element.getAttribute('data-variant');
    document.querySelector("input[name='answer']").value = variant;
}

function markFormSubmitted() {
    isSubmittable = true;
}

function addListeners() {
    const answers = document.getElementById('answers');
    // const answers = document.querySelector('.question_answers');

    answers.addEventListener('click', event => {
        let target = event.target;
        let isChosen = target.classList.contains('card') || target.tagName == 'LABEL' || target.tagName == 'INPUT';
        if (isChosen) {
            deleteClassChosen();
            chooseCard(target);
        }
    })
}

function chooseCard(target) {
    let parent = target.closest('.card');

    parent.classList.add('chosenCard');
}

function deleteClassChosen() {
    const cards = document.querySelectorAll('.card');

    cards.forEach(elem => {
        elem.classList.remove('chosenCard');
    })
}