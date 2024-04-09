//добавление урока без записи в бд
function addLesson() {
    fetch('/courses/settings/add-lesson', {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // Преобразуем ответ в JSON
        })
        .then(data => {
            // В переменной 'data' будет содержаться ответ от сервера (в данном случае - список lessonInfoResponse)
            console.log('Список уроков:', data);
            // Здесь можно выполнить дальнейшие действия с полученным списком
        })
}