(() => {
    const options = Array.isArray(window.categoryOptions) ? window.categoryOptions : [];
    const normalizedOptions = options.map((option) => ({
        id: option.id,
        nameKo: option.nameKo || '',
        nameEn: option.nameEn || '',
        source: option.source || '',
        searchText: `${option.nameKo || ''} ${option.nameEn || ''} ${option.source || ''}`.toLowerCase()
    }));

    const wrappers = document.querySelectorAll('[data-tag-input]');
    wrappers.forEach((wrapper) => initTagInput(wrapper));

    function initTagInput(wrapper) {
        const hiddenId = wrapper.dataset.hidden;
        const hiddenInput = hiddenId ? document.getElementById(hiddenId) : null;
        const selectedCsv = wrapper.dataset.selected || '';
        const selectedIds = selectedCsv
            .split(',')
            .map((value) => Number(value.trim()))
            .filter((value) => Number.isFinite(value) && value > 0);
        const selectedSet = new Set(selectedIds);

        const input = wrapper.querySelector('.tag-input__control');
        const tagContainer = wrapper.querySelector('.tag-input__items');
        const suggestions = wrapper.querySelector('.tag-suggestions');

        if (!input || !tagContainer || !suggestions) {
            return;
        }

        const updateHidden = () => {
            if (hiddenInput) {
                hiddenInput.value = selectedIds.join(',');
            }
        };

        const addTag = (option) => {
            if (!option || selectedSet.has(option.id)) {
                return;
            }
            selectedSet.add(option.id);
            selectedIds.push(option.id);
            tagContainer.appendChild(renderTag(option));
            updateHidden();
        };

        const removeTag = (id) => {
            selectedSet.delete(id);
            const index = selectedIds.indexOf(id);
            if (index >= 0) {
                selectedIds.splice(index, 1);
            }
            const chip = tagContainer.querySelector(`[data-tag-id="${id}"]`);
            if (chip) {
                chip.remove();
            }
            updateHidden();
        };

        const renderTag = (option) => {
            const chip = document.createElement('span');
            chip.className = 'tag-chip';
            chip.dataset.tagId = option.id;

            const label = document.createElement('span');
            label.textContent = option.nameKo;
            chip.appendChild(label);

            const removeButton = document.createElement('button');
            removeButton.type = 'button';
            removeButton.setAttribute('aria-label', '태그 삭제');
            removeButton.textContent = '×';
            removeButton.addEventListener('click', () => removeTag(option.id));
            chip.appendChild(removeButton);

            return chip;
        };

        const renderSuggestions = (query) => {
            const normalizedQuery = query.trim().toLowerCase();
            if (!normalizedQuery) {
                suggestions.classList.remove('is-open');
                return;
            }
            const matches = normalizedOptions.filter((option) => {
                if (selectedSet.has(option.id)) {
                    return false;
                }
                return option.searchText.includes(normalizedQuery);
            }).slice(0, 10);

            suggestions.innerHTML = '';
            if (matches.length === 0) {
                suggestions.classList.remove('is-open');
                return;
            }

            matches.forEach((option) => {
                const item = document.createElement('button');
                item.type = 'button';
                item.className = 'tag-suggestion';

                const title = document.createElement('span');
                title.className = 'tag-suggestion__title';
                title.textContent = option.nameKo;
                item.appendChild(title);

                const meta = document.createElement('span');
                meta.className = 'tag-suggestion__meta';
                const metaPieces = [option.nameEn, option.source].filter(Boolean);
                meta.textContent = metaPieces.join(' · ');
                item.appendChild(meta);

                item.addEventListener('click', () => {
                    addTag(option);
                    input.value = '';
                    renderSuggestions('');
                });

                suggestions.appendChild(item);
            });

            suggestions.classList.add('is-open');
        };

        selectedIds.forEach((id) => {
            const option = normalizedOptions.find((item) => item.id === id);
            if (option) {
                tagContainer.appendChild(renderTag(option));
            }
        });
        updateHidden();

        input.addEventListener('input', () => {
            renderSuggestions(input.value);
        });

        input.addEventListener('focus', () => {
            renderSuggestions(input.value);
        });

        input.addEventListener('keydown', (event) => {
            if (event.key !== 'Enter') {
                return;
            }
            event.preventDefault();
            const query = input.value.trim().toLowerCase();
            if (!query) {
                return;
            }
            const match = normalizedOptions.find((option) => option.searchText.includes(query) && !selectedSet.has(option.id));
            if (match) {
                addTag(match);
                input.value = '';
                renderSuggestions('');
            }
        });

        document.addEventListener('click', (event) => {
            if (!wrapper.contains(event.target)) {
                suggestions.classList.remove('is-open');
            }
        });
    }
})();
