<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecommercyApp.state.home.createOrEditLabel"
          data-cy="StateCreateUpdateHeading"
          v-text="$t('ecommercyApp.state.home.createOrEditLabel')"
        >
          Create or edit a State
        </h2>
        <div>
          <div class="form-group" v-if="state.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="state.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.state.name')" for="state-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="state-name"
              data-cy="name"
              :class="{ valid: !$v.state.name.$invalid, invalid: $v.state.name.$invalid }"
              v-model="$v.state.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.state.country')" for="state-country">Country</label>
            <select class="form-control" id="state-country" data-cy="country" name="country" v-model="state.country">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="state.country && countryOption.id === state.country.id ? state.country : countryOption"
                v-for="countryOption in countries"
                :key="countryOption.id"
              >
                {{ countryOption.id }}
              </option>
            </select>
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
            :disabled="$v.state.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./state-update.component.ts"></script>
