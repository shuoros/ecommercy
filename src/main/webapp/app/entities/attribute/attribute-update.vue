<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecommercyApp.attribute.home.createOrEditLabel"
          data-cy="AttributeCreateUpdateHeading"
          v-text="$t('ecommercyApp.attribute.home.createOrEditLabel')"
        >
          Create or edit a Attribute
        </h2>
        <div>
          <div class="form-group" v-if="attribute.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="attribute.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.attribute.key')" for="attribute-key">Key</label>
            <input
              type="text"
              class="form-control"
              name="key"
              id="attribute-key"
              data-cy="key"
              :class="{ valid: !$v.attribute.key.$invalid, invalid: $v.attribute.key.$invalid }"
              v-model="$v.attribute.key.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.attribute.value')" for="attribute-value">Value</label>
            <input
              type="text"
              class="form-control"
              name="value"
              id="attribute-value"
              data-cy="value"
              :class="{ valid: !$v.attribute.value.$invalid, invalid: $v.attribute.value.$invalid }"
              v-model="$v.attribute.value.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.attribute.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./attribute-update.component.ts"></script>
