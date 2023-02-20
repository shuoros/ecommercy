<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="state">
        <h2 class="jh-entity-heading" data-cy="stateDetailsHeading">
          <span v-text="$t('ecommercyApp.state.detail.title')">State</span> {{ state.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('ecommercyApp.state.name')">Name</span>
          </dt>
          <dd>
            <span>{{ state.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.state.country')">Country</span>
          </dt>
          <dd>
            <div v-if="state.country">
              <router-link :to="{ name: 'CountryView', params: { countryId: state.country.id } }">{{ state.country.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="state.id" :to="{ name: 'StateEdit', params: { stateId: state.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./state-details.component.ts"></script>
